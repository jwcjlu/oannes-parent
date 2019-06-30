package com.jwcjlu.oannes;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import com.jwcjlu.oannes.config.annotation.OannesEnable;
import org.apache.curator.test.TestingServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jwcjlu.oannes.config.ConsumerBean;
import com.jwcjlu.oannes.register.listener.NotifyListener;
import sun.jvm.hotspot.HelloWorld;

public class AppMain {
	private TestingServer server;

	@Configuration
	@OannesEnable(scanBasePackages = "com.jwcjlu.springbean")
	@ComponentScan(value = {"org.apache"})
	@PropertySource("classpath:/spring/oannes-consumer.properties")
	static class ConsumerConfiguration {

	}
	@Before
	public void setup() throws Exception {
		server = new TestingServer(2181);
		server.start();
	}
	@After
	public void destory() throws IOException {
		server.stop();
	}

	@Test
	public void test() throws IOException {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsumerConfiguration.class);
		context.start();
		System.in.read();
	}
}
