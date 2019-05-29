package top.shenluw.sldp.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import top.shenluw.sldp.SldpJsonException;

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
    protected Object jsonToObject(Class<?> targetClass, String jsonData, MethodParameter parameter, NativeWebRequest webRequest) {
        try {
            Object obj;
            if (isSecure(parameter, targetClass)) {
                byte[] decrypt = getEncryptor().decrypt(jsonData);
                obj = objectMapper.readValue(decrypt, targetClass);
            } else {
                obj = objectMapper.readValue(jsonData, targetClass);
            }
            return obj;
        } catch (IOException e) {
            throw new SldpJsonException("sldp json data not valid", e);
        }
    }
}
