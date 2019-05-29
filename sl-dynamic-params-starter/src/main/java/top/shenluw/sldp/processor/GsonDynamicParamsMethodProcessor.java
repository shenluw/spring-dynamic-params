package top.shenluw.sldp.processor;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import org.slf4j.Logger;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import top.shenluw.sldp.Encryptor;
import top.shenluw.sldp.SldpException;
import top.shenluw.sldp.SldpJsonException;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Shenluw
 * 创建日期：2019/2/20 18:01
 */
public class GsonDynamicParamsMethodProcessor extends JsonDynamicParamsMethodProcessor {
    private final static Logger log = getLogger(GsonDynamicParamsMethodProcessor.class);

    private Gson gson;

    public GsonDynamicParamsMethodProcessor(String dataName, Gson gson) {
        super(dataName);
        this.gson = gson;
    }

    @Override
    protected Object jsonToObject(Class<?> targetClass, String jsonData, MethodParameter parameter, NativeWebRequest webRequest) throws SldpJsonException {
        try {
            Object obj;
            if (isSecure(parameter, targetClass)) {
                Encryptor encryptor = getEncryptor();
                byte[] bytes = encryptor.decrypt(jsonData);
                obj = gson.fromJson(new InputStreamReader(new ByteArrayInputStream(bytes), encryptor.getCharset()), targetClass);
            } else {
                obj = gson.fromJson(jsonData, targetClass);
            }
            return obj;
        } catch (JsonParseException e) {
            throw new SldpException("sldp json data not valid", e);
        }
    }
}
