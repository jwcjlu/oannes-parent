package com.jwcjlu.oannes.config;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OannService {
	String id() default "";
	Class interfaces();
	String group() default "";
	String version() default"";
	int port() default 8888;

}
