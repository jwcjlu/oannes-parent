package com.jwcjlu.oannes;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppMain {
	public static void main(String[] args) throws IOException {
		ApplicationContext  ac=new ClassPathXmlApplicationContext("spring.xml");
		System.in.read();
	}
}
