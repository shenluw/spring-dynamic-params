package top.shenluw.sldp;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import top.shenluw.sldp.annotation.Sldp;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @author Shenluw
 * 创建日期：2019/2/18 17:38
 */
public abstract class AbstractDynamicParamsMethodProcessor implements HandlerMethodArgumentResolver, BeanClassLoaderAware {

    private String typeName;

    private boolean defaultProcessor;

    private TypeNameAliasResolver typeNameAliasResolver;

    private ClassLoader classLoader;

    public boolean isDefaultProcessor() {
        return defaultProcessor;
    }

    public void setDefaultProcessor(boolean defaultProcessor) {
        this.defaultProcessor = defaultProcessor;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public TypeNameAliasResolver getTypeNameAliasResolver() {
        return typeNameAliasResolver;
    }

    public void setTypeNameAliasResolver(TypeNameAliasResolver typeNameAliasResolver) {
        this.typeNameAliasResolver = typeNameAliasResolver;
    }

    public abstract ModelType getModelType();

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> type = parameter.getParameterType();

        Sldp sldp = parameter.getParameterAnnotation(Sldp.class);
        if (sldp != null && DynamicParamsProcessorUtils.support(sldp, getModelType(), defaultProcessor))
            return true;

        if (defaultProcessor && DynamicModel.class.isAssignableFrom(type)) {
            return true;
        }
        Set<Sldp> sldps = AnnotationUtils.getRepeatableAnnotations(type, Sldp.class);
        if (!sldps.isEmpty()) {
            return DynamicParamsProcessorUtils.support(sldps.iterator().next(), getModelType(), defaultProcessor);
        }
        return false;
    }

    protected String getRealClass(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) {
        String typeName = this.typeName;
        Sldp sldp = parameter.getParameterAnnotation(Sldp.class);
        if (sldp == null) {
            Class<?> type = parameter.getParameterType();
            if (!DynamicModel.class.isAssignableFrom(type)) {
                Set<Sldp> sldps = AnnotationUtils.getRepeatableAnnotations(type, Sldp.class);
                if (!sldps.isEmpty()) {
                    sldp = sldps.iterator().next();
                }
            }
        }
        if (sldp != null) {
            String name = sldp.name();
            if (!"".equals(name)) {
                typeName = name;
            }
        }
        String className = webRequest.getParameter(typeName);
        if (typeNameAliasResolver != null) {
            return typeNameAliasResolver.resolver(className);
        }
        return className;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String realClassName = getRealClass(parameter, mavContainer, webRequest);
        Assert.hasText(realClassName, "sldp requst must has real class type");

        Class<?> realClass = ClassUtils.forName(realClassName, classLoader);
        if (!ClassUtils.isAssignable(parameter.getParameterType(), realClass)) {
            throw new IllegalStateException("sldp real class [ " + realClassName + " ] not cast [ " + parameter.getParameterType().getName() + " ]");
        }

        Assert.hasText(realClassName, "sldp real class must be has text ");
        return bind(realClass, parameter, mavContainer, webRequest, binderFactory);
    }

    protected abstract Object bind(Class<?> targetClass, MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception;

    protected void validate(WebDataBinder binder, MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        validateIfApplicable(binder, parameter);
        if (binder.getBindingResult().hasErrors() && isBindExceptionRequired(binder, parameter)) {
            throw new BindException(binder.getBindingResult());
        }
    }

    protected void validateIfApplicable(WebDataBinder binder, MethodParameter parameter) {
        for (Annotation ann : parameter.getParameterAnnotations()) {
            Object[] validationHints = determineValidationHints(ann);
            if (validationHints != null) {
                binder.validate(validationHints);
                break;
            }
        }
    }

    protected boolean isBindExceptionRequired(WebDataBinder binder, MethodParameter parameter) {
        return isBindExceptionRequired(parameter);
    }

    protected boolean isBindExceptionRequired(MethodParameter parameter) {
        int i = parameter.getParameterIndex();
        Class<?>[] paramTypes = parameter.getExecutable().getParameterTypes();
        boolean hasBindingResult = (paramTypes.length > (i + 1) && Errors.class.isAssignableFrom(paramTypes[i + 1]));
        return !hasBindingResult;
    }

    @Nullable
    private Object[] determineValidationHints(Annotation ann) {
        Validated validatedAnn = AnnotationUtils.getAnnotation(ann, Validated.class);
        if (validatedAnn != null || ann.annotationType().getSimpleName().startsWith("Valid")) {
            Object hints = (validatedAnn != null ? validatedAnn.value() : AnnotationUtils.getValue(ann));
            if (hints == null) {
                return new Object[0];
            }
            return (hints instanceof Object[] ? (Object[]) hints : new Object[]{hints});
        }
        return null;
    }

}
