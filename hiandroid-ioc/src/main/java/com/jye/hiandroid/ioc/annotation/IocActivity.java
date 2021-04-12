package com.jye.hiandroid.ioc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author jye
 * @since 1.0
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface IocActivity {
    String value() default "";

    /**
     * set content view.
     *
     * @since 1.0.1
     */
    int layout() default 0;
}
