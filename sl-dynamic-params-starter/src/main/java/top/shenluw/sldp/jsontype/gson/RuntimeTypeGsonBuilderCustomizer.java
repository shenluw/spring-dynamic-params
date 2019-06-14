package top.shenluw.sldp.jsontype.gson;

import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.boot.autoconfigure.gson.GsonBuilderCustomizer;
import top.shenluw.sldp.SldpException;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Shenluw
 * created：2019/6/14 21:29
 */
public class RuntimeTypeGsonBuilderCustomizer implements GsonBuilderCustomizer, BeanClassLoaderAware {
    private final static Logger log = LoggerFactory.getLogger(RuntimeTypeAdapterFactory.class);

    private final Map<String, String> typeAlias;
    private final String              fieldName;

    private ClassLoader classLoader;

    public RuntimeTypeGsonBuilderCustomizer(Map<String, String> typeAlias, String fieldName) {
        this.typeAlias = typeAlias;
        this.fieldName = fieldName;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void customize(GsonBuilder builder) {

        ClassView view = new ClassView();

        typeAlias.forEach((key, type) -> {
            try {
                Class<?> clazz = classLoader.loadClass(type);
                view.put(new ClassNode(key, clazz));
            } catch (ClassNotFoundException e) {
                throw new SldpException("register gson subtype not found", e);
            }

        });

        for (ClassNode node : view.nodes) {
            if (node.child != null && node.child.size() > 0) {
                RuntimeTypeAdapterFactory factory = RuntimeTypeAdapterFactory.of(node.type, fieldName);
                for (ClassNode childNode : node.child) {
                    factory.registerSubtype(childNode.type, childNode.key);
                    log.debug("register gson super: {}, subtype: {}", node.type, childNode.type);
                }
                builder.registerTypeAdapterFactory(factory);
            }
        }
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    private class ClassNode {
        String         key;
        Class          type;
        Set<ClassNode> child;

        public ClassNode(String key, Class type) {
            this.key = key;
            this.type = type;
        }
    }


    private class ClassView {
        Set<ClassNode> nodes = new HashSet<>(8);

        /**
         * 收集父类，并做映射关系
         *
         * @param node
         */
        void put(ClassNode node) {
            Class type = node.type;
            Class last = null;

            Class superClass = type.getSuperclass();
            while (superClass != Object.class) {
                last = superClass;
                superClass = superClass.getSuperclass();
            }
            /* 此时自己就是最上层 */
            if (last == null) {
                last = type;
            }

            ClassNode parent = null;
            for (ClassNode classNode : nodes) {
                if (classNode.type == last) {
                    parent = classNode;
                    break;
                }
            }

            if (parent == null) {
                parent = new ClassNode(null, last);
                nodes.add(parent);
            }
            if (parent.child == null) {
                parent.child = new HashSet<>(4);
            }
            parent.child.add(node);
        }
    }


}
