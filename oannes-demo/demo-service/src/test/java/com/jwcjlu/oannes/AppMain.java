package com.jwcjlu.oannes;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jwcjlu.oannes.config.ConsumerBean;
import com.jwcjlu.oannes.register.listener.NotifyListener;

public class AppMain {
	public static void main(String[] args) throws IOException {
		ApplicationContext  ac=new ClassPathXmlApplicationContext("spring.xml");
		System.in.read();
	
	}
}
