package com.jwcjlu.oannes.common.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
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
