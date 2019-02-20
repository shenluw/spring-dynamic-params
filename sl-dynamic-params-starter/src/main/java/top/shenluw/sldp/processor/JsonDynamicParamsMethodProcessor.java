package top.shenluw.sldp.processor;

import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import top.shenluw.sldp.AbstractDynamicParamsMethodProcessor;
import top.shenluw.sldp.ModelType;
import top.shenluw.sldp.annotation.Sldp;

import java.util.Set;

/**
 * @author Shenluw
 * 创建日期：2019/2/20 10:47
 */
public abstract class JsonDynamicParamsMethodProcessor extends AbstractDynamicParamsMethodProcessor {

    private String dataName;

    public String getDataName() {
        return dataName;
    }

    public JsonDynamicParamsMethodProcessor(String dataName) {
        this.dataName = dataName;
        Assert.hasText(dataName, "sldp json data name must has text");
    }

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
