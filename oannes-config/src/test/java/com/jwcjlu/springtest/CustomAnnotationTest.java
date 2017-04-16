package com.jwcjlu.springtest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jwcjlu.springbean.HelloWorld;

public class CustomAnnotationTest {
	public static void main(String[] args) {
		ApplicationContext  ac=new ClassPathXmlApplicationContext("spring.xml");
		HelloWorld hw=ac.getBean(HelloWorld.class);
		hw.say("aaaa");
		
	}
}
