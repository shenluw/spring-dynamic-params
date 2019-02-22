package top.shenluw.sldp;

import java.util.Collection;

/**
 * 当存在多个同类型的处理器时可以实现这个接口进行排序或者一些其他操作
 *
 * @author Shenluw
 * 创建日期：2019/2/20 18:13
 */
@FunctionalInterface
public interface DynamicParamsMethodProcessorCustomizer {
    Collection<AbstractDynamicParamsMethodProcessor> customize(Collection<AbstractDynamicParamsMethodProcessor> resolvers);
}