package top.shenluw.sldp.processor;

import org.springframework.util.Assert;
import top.shenluw.sldp.AbstractDynamicParamsMethodProcessor;
import top.shenluw.sldp.ModelType;

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
}
