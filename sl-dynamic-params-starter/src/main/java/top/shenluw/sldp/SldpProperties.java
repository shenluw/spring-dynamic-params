package top.shenluw.sldp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import top.shenluw.sldp.processor.WebDataBinderDynamicParamsMethodProcessor;

/**
 * @author Shenluw
 * 创建日期：2019/2/20 11:32
 */
@ConfigurationProperties(prefix = "sldp")
public class SldpProperties {

    private boolean enable = true;
    private String typeName = Constants.TYPE_NAME;
    private String jsonDataName = Constants.JSON_DATA_NAME;
    private String defaultProcessor = WebDataBinderDynamicParamsMethodProcessor.class.getName();

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getJsonDataName() {
        return jsonDataName;
    }

    public void setJsonDataName(String jsonDataName) {
        this.jsonDataName = jsonDataName;
    }

    public String getDefaultProcessor() {
        return defaultProcessor;
    }

    public void setDefaultProcessor(String defaultProcessor) {
        this.defaultProcessor = defaultProcessor;
    }

}
