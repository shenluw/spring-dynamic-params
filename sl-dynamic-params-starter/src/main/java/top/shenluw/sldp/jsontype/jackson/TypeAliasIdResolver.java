package top.shenluw.sldp.jsontype.jackson;

import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsontype.impl.ClassNameIdResolver;
import com.fasterxml.jackson.databind.type.TypeFactory;
import top.shenluw.sldp.TypeNameAliasResolver;

import java.io.IOException;

/**
 * @author Shenluw
 * 创建日期：2019/6/14 16:56
 */
public class TypeAliasIdResolver extends ClassNameIdResolver {
    private TypeNameAliasResolver resolver;

    public TypeAliasIdResolver(JavaType baseType, TypeFactory typeFactory, TypeNameAliasResolver resolver) {
        super(baseType, typeFactory);
        this.resolver = resolver;
    }

    @Override
    public String idFromValue(Object value) {
        return resolver.typeToName(value.getClass().getName());
    }

    @Override
    public JavaType typeFromId(DatabindContext context, String id) throws IOException {
        return super.typeFromId(context, resolver.resolver(id));
    }
}
