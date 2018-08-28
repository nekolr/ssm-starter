package com.nekolr.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PropertiesBean {
    /**
     * 前缀
     *
     * @return
     */
    String prefix();
}
