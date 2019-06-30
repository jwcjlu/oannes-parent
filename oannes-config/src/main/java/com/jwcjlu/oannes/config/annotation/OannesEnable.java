package com.jwcjlu.oannes.config.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@OannesComponentScan
public @interface OannesEnable {
    @AliasFor(annotation = OannesComponentScan.class, attribute = "basePackages")
    String[] scanBasePackages() default {};

}
