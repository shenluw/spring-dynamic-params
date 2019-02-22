package top.shenluw.sldp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import top.shenluw.sldp.processor.WebDataBinderDynamicParamsMethodProcessor;

import java.util.Map;

/**
 * @author Shenluw
 * 创建日期：2019/2/20 11:32
 */
@ConfigurationProperties(prefix = "sldp")
public class SldpProperties {

    private boolean enable = true;
    private boolean enableSecurity = false;
    private boolean defaultSecurity = false;
    private String typeName = Constants.TYPE_NAME;
    private String jsonDataName = Constants.JSON_DATA_NAME;
    private String defaultProcessor = WebDataBinderDynamicParamsMethodProcessor.class.getName();
    private Map<String, String> typeAlias;

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

    public Map<String, String> getTypeAlias() {
        return typeAlias;
    }

    public void setTypeAlias(Map<String, String> typeAlias) {
        this.typeAlias = typeAlias;
    }

    public boolean isEnableSecurity() {
        return enableSecurity;
    }

    public void setEnableSecurity(boolean enableSecurity) {
        this.enableSecurity = enableSecurity;
    }

    public boolean isDefaultSecurity() {
        return defaultSecurity;
    }

    public void setDefaultSecurity(boolean defaultSecurity) {
        this.defaultSecurity = defaultSecurity;
    }
}
