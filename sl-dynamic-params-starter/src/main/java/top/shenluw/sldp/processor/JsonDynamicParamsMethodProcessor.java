package top.shenluw.sldp.processor;

import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import top.shenluw.sldp.AbstractSecureDynamicParamsMethodProcessor;
import top.shenluw.sldp.ModelType;
import top.shenluw.sldp.SldpJsonException;
import top.shenluw.sldp.annotation.Sldp;

import java.util.Set;

/**
 * @author Shenluw
 * 创建日期：2019/2/20 10:47
 */
public abstract class JsonDynamicParamsMethodProcessor extends AbstractSecureDynamicParamsMethodProcessor {

    private String dataName;

    public String getDataName() {
        return dataName;
    }

    public JsonDynamicParamsMethodProcessor(String dataName) {
        this.dataName = dataName;
        Assert.hasText(dataName, "sldp json data name must has text");
    }

    @Override
    protected Object bind(Class<?> targetClass, MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String data = webRequest.getParameter(getRealDataName(parameter, mavContainer, webRequest));
        if (!StringUtils.hasText(data)) {
            WebDataBinder binder = binderFactory.createBinder(webRequest, null, parameter.getParameterName());
            validate(binder, parameter, mavContainer, webRequest);
            return null;
        }
        Object obj = jsonToObject(targetClass, data, parameter, webRequest);
        WebDataBinder binder = binderFactory.createBinder(webRequest, obj, parameter.getParameterName());
        validate(binder, parameter, mavContainer, webRequest);
        return obj;
    }

    protected abstract Object jsonToObject(Class<?> targetClass, String jsonData, MethodParameter parameter, NativeWebRequest webRequest) throws SldpJsonException;

    @Override
    public ModelType getModelType() {
        return ModelType.Json;
    }

    protected String getRealDataName(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) {
        Sldp sldp = parameter.getParameterAnnotation(Sldp.class);
        if (sldp == null) {
            Class<?> type = parameter.getParameterType();
            Set<Sldp> sldps = AnnotationUtils.getRepeatableAnnotations(type, Sldp.class);
            if (!sldps.isEmpty()) {
                sldp = sldps.iterator().next();
            }
        }
        if (sldp == null) return getDataName();
        if (StringUtils.hasText(sldp.dataName())) return sldp.dataName();
        return getDataName();
    }

}
