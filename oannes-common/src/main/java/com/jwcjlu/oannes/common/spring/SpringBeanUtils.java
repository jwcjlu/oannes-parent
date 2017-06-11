package com.jwcjlu.oannes.common.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
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
		return context.getBean(clazz);
	}
	public static  void setContext(ApplicationContext applicationContext){
		context=applicationContext;
	}

}
