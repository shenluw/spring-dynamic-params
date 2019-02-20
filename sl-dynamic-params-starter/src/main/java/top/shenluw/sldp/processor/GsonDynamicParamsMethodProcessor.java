package top.shenluw.sldp.processor;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import org.slf4j.Logger;
import org.springframework.core.MethodParameter;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

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
    protected Object bind(String className, MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Class<?> type = ClassUtils.forName(className, this.getClass().getClassLoader());

        String data = webRequest.getParameter(getRealDataName(parameter, mavContainer, webRequest));
        try {
            Object obj = gson.fromJson(data, type);
            WebDataBinder binder = binderFactory.createBinder(webRequest, obj, parameter.getParameterName());
            validate(binder, parameter, mavContainer, webRequest);
            return obj;
        } catch (JsonParseException e) {
            log.warn("sldp json data not valid");
            throw e;
        }
    }
}
