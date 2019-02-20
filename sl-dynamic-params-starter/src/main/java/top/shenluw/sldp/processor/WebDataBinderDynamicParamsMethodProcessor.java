package top.shenluw.sldp.processor;

import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import top.shenluw.sldp.AbstractDynamicParamsMethodProcessor;
import top.shenluw.sldp.ModelType;

import javax.servlet.ServletRequest;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Shenluw
 * 创建日期：2019/2/20 10:25
 */
public class WebDataBinderDynamicParamsMethodProcessor extends AbstractDynamicParamsMethodProcessor {
    private final static Logger log = getLogger(WebDataBinderDynamicParamsMethodProcessor.class);

    @Override
    public ModelType getModelType() {
        return ModelType.NameValue;
    }

    @Override
    protected Object bind(String className, MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Object obj = createObject(className, parameter, mavContainer, webRequest);
        WebDataBinder binder = binderFactory.createBinder(webRequest, obj, parameter.getParameterName());
        ServletRequest servletRequest = webRequest.getNativeRequest(ServletRequest.class);
        Assert.state(servletRequest != null, "No ServletRequest");

        ServletRequestDataBinder servletBinder = (ServletRequestDataBinder) binder;
        servletBinder.bind(servletRequest);
        validate(binder, parameter, mavContainer, webRequest);
        log.info("in  {}  {} {}", obj, obj.hashCode(), binder.getTarget());
        return obj;
    }

    protected Object createObject(String className, MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) {
        try {
            Class<?> realType = ClassUtils.forName(className, getClass().getClassLoader());
            Constructor<?> constructor = BeanUtils.findPrimaryConstructor(realType);
            if (constructor == null) {
                constructor = realType.getConstructor();
                if (constructor == null)
                    constructor = realType.getDeclaredConstructor();
            }
            Assert.notNull(constructor, "sldp real type must has no args constructor");
            return constructor.newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            log.warn("sldp create object error: {}", e.getMessage());
        }
        return null;
    }

}
