package com.jwcjlu.oannes.common.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ClassUtils;

import java.beans.Introspector;

/**
 * 
 * <pre>
 * spring Bean的工具类
 *  File: SpringBeanUtils.java
 * 
 *  Copyright (c) 2017, com.jwcjlu All Rights Reserved.
 * 
 *  Description:
 *  TODO
 * 
 *  Revision History
 *  Date,					Who,					What;
 *  2017年6月11日				jinwei				Initial.
 *
 * </pre>
 */
public class SpringBeanUtils implements ApplicationContextAware{
	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		context=applicationContext;
	}
	public static <T> T getBean(Class<T> clazz){

		try {
			return context.getBean(clazz);
		}catch (Exception e){
			String shortClassName = ClassUtils.getShortName(clazz);
			return (T)context.getBean(Introspector.decapitalize(shortClassName));
		}
	}
	public static  void setContext(ApplicationContext applicationContext){
		context=applicationContext;
	}

}
