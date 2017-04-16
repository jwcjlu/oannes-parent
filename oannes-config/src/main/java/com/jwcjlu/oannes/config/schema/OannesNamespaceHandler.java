package com.jwcjlu.oannes.config.schema;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import com.jwcjlu.oannes.config.spring.BeanScannerConfigurer;

public class OannesNamespaceHandler extends NamespaceHandlerSupport{

	@Override
	public void init() {
		// TODO Auto-generated method stub
		 registerBeanDefinitionParser("annotation", new OannesBeanDefinitionParser(BeanScannerConfigurer.class, true));
	}

}
