package com.jwcjlu.oannes;

import com.jwcjlu.oannes.config.annotation.OannesEnable;
import org.apache.curator.test.TestingServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;


public class ProviderSericeMain {
	private TestingServer server;

	@Configuration
	@OannesEnable(scanBasePackages = "com.jwcjlu.oannes")
	@ComponentScan(value = {"org.apache"})
	@PropertySource("classpath:/spring/oannes-consumer.properties")
	static class ConsumerConfiguration {

	}

	@Before
	public void setup() throws Exception {
		server = new TestingServer(2181);
		server.start();
	}

	@Test
	public void test() throws IOException {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsumerConfiguration.class);
		context.start();
		System.out.println("load success");
		System.in.read();
	}
	@After
	public void destroy() throws IOException {
		server.stop();
	}
}
