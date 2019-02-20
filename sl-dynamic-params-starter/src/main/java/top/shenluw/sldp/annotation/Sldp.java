package top.shenluw.sldp.annotation;

import top.shenluw.sldp.ModelType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Shenluw
 * 创建日期：2019/2/18 18:09
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Sldp {
    String name() default "";

    String dataName() default "";

    ModelType type() default ModelType.Default;
}

