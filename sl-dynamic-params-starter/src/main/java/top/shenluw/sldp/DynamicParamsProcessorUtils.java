package top.shenluw.sldp;

import top.shenluw.sldp.annotation.Sldp;

/**
 * @author Shenluw
 * 创建日期：2019/2/20 10:42
 */
public class DynamicParamsProcessorUtils {

    static boolean support(Sldp sldp, ModelType type, boolean defaultProcessor) {
        ModelType modelType = sldp.type();
        if (modelType == type) {
            return true;
        }
        if (modelType == ModelType.Default && defaultProcessor) {
            return true;
        }
        return false;
    }

}
