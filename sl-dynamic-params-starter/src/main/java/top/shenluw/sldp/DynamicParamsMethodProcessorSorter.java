package top.shenluw.sldp;

import java.util.Collection;

/**
 * 当存在多个同类型的处理器时可以实现这个接口进行排序
 *
 * @author Shenluw
 * 创建日期：2019/2/20 18:13
 */
public interface DynamicParamsMethodProcessorSorter {
    Collection<AbstractDynamicParamsMethodProcessor> sort(Collection<AbstractDynamicParamsMethodProcessor> resolvers);
}
