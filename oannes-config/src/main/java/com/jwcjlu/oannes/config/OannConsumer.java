package com.jwcjlu.oannes.config;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OannConsumer {
	String backupAddress() default "localhost:2181";
	int port() default 8888;
	String host() default "localhost";
	String group() default "";
	String version() default"";
	Class interfaces();

}
