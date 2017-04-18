package com.jwcjlu.oannes.config.schema;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.jwcjlu.oannes.config.spring.BeanScannerConfigurer;

public class OannesBeanDefinitionParser implements BeanDefinitionParser {
    private final Class<?> beanClass;
    
    private final boolean required;

	public OannesBeanDefinitionParser(Class<?> beanClass, boolean required) {
		// TODO Auto-generated constructor stub
		this.beanClass=beanClass;
		this.required=required;
	}

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		// TODO Auto-generated method stub
		 return parse(element, parserContext, beanClass, required);
	}

	private BeanDefinition parse(Element element, ParserContext parserContext, Class<?> beanClass, boolean required) {
		// TODO Auto-generated method stub
		 RootBeanDefinition beanDefinition = new RootBeanDefinition();
	        beanDefinition.setBeanClass(beanClass);
	        beanDefinition.setLazyInit(false);
	        String id = element.getAttribute("id");
	        if ((id == null || id.length() == 0) && required) {
	        	String generatedBeanName = element.getAttribute("name");
	     
	        	if (generatedBeanName == null || generatedBeanName.length() == 0) {
	        		generatedBeanName = beanClass.getName();
	        	}
	            id = generatedBeanName; 
	            int counter = 2;
	            while(parserContext.getRegistry().containsBeanDefinition(id)) {
	                id = generatedBeanName + (counter ++);
	            }
	        }
	        if (id != null && id.length() > 0) {
	            if (parserContext.getRegistry().containsBeanDefinition(id))  {
	        		throw new IllegalStateException("Duplicate spring bean id " + id);
	        	}
	            parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);
	            beanDefinition.getPropertyValues().addPropertyValue("id", id);
	        }
	   
	        Set<String> props = new HashSet<String>();
	        ManagedMap parameters = null;
	  
	        NamedNodeMap attributes = element.getAttributes();
	        int len = attributes.getLength();
	        for (int i = 0; i < len; i++) {
	            Node node = attributes.item(i);
	            String name = node.getLocalName();
	            if (! props.contains(name)) {
	                if (parameters == null) {
	                    parameters = new ManagedMap();
	                }
	                String value = node.getNodeValue();
	                parameters.put(name, new TypedStringValue(value, String.class));
	            }
	        }
	        if (parameters != null) {
	            beanDefinition.getPropertyValues().addPropertyValue("parameters", parameters);
	        }
	        return beanDefinition;
	}

}
