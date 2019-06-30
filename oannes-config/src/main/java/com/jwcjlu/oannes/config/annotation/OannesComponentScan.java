package com.jwcjlu.oannes.config.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ComponenetScanRegister.class)
public @interface OannesComponentScan {

    String[] value() default {};

    String[] basePackages() default {};
}
