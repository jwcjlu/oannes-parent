package com.jwcjlu.springtest;

import com.jwcjlu.oannes.config.annotation.OannesEnable;
import com.jwcjlu.springbean.HelloController;
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

import com.jwcjlu.springbean.HelloWorld;

import java.io.IOException;

public class CustomAnnotationTest {

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
        HelloController helloWorld = (HelloController) context.getBean("helloController");
        String msg = helloWorld.say("hello");
        System.out.println(msg);
        System.in.read();
    }

}
