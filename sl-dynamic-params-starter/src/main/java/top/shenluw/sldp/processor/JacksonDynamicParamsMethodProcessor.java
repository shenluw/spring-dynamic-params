package top.shenluw.sldp.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.core.MethodParameter;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Shenluw
 * 创建日期：2019/2/20 10:47
 */
public class JacksonDynamicParamsMethodProcessor extends JsonDynamicParamsMethodProcessor {
    private final static Logger log = getLogger(JacksonDynamicParamsMethodProcessor.class);

    private ObjectMapper objectMapper;

    public JacksonDynamicParamsMethodProcessor(String dataName, ObjectMapper objectMapper) {
        super(dataName);
        this.objectMapper = objectMapper;
    }

    @Override
    protected Object bind(String className, MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Class<?> type = ClassUtils.forName(className, this.getClass().getClassLoader());

        String data = webRequest.getParameter(getRealDataName(parameter, mavContainer, webRequest));
        try {
            Object obj = objectMapper.readValue(data, type);
            WebDataBinder binder = binderFactory.createBinder(webRequest, obj, parameter.getParameterName());
            validate(binder, parameter, mavContainer, webRequest);
            log.info("in  {}  {} {}", obj, obj.hashCode(), binder.getTarget());

            return obj;
        } catch (IOException e) {
            log.warn("sldp json data not valid");
            throw e;
        }
    }

}