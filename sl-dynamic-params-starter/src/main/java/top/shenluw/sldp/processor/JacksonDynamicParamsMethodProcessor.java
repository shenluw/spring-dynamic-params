package top.shenluw.sldp.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.core.MethodParameter;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import top.shenluw.sldp.SldpException;

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
        if (!StringUtils.hasText(data)) {
            WebDataBinder binder = binderFactory.createBinder(webRequest, null, parameter.getParameterName());
            validate(binder, parameter, mavContainer, webRequest);
            return null;
        }
        try {
            Object obj;
            if (isSecure(parameter, type)) {
                byte[] decrypt = getEncryptor().decrypt(data);
                obj = objectMapper.readValue(decrypt, type);
            } else {
                obj = objectMapper.readValue(data, type);
            }
            WebDataBinder binder = binderFactory.createBinder(webRequest, obj, parameter.getParameterName());
            validate(binder, parameter, mavContainer, webRequest);
            return obj;
        } catch (IOException e) {
            throw new SldpException("sldp json data not valid", e);
        }
    }

}
