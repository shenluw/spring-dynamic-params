package top.shenluw.sldp.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 被转换的参数是否是加密的
 *
 * @author Shenluw
 * 创建日期：2019/2/22 10:38
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface SlSecurity {
    boolean enable() default true;
}
