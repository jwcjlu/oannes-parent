package com.jwcjlu.oannes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.jwcjlu.oannes.config.OannConsumer;

@Service
public class DemoAction {
	@OannConsumer(interfaces=DemoService.class)
	private DemoService demoService;
	@OannConsumer(interfaces=HelloService.class)
	private HelloService  service;
	public void start() throws Exception {
        for (int i = 0; i < Integer.MAX_VALUE; i ++) {
            try {
            	String hello = demoService.sayHello("world" + i);
            	/*service.sayHello();*/
                System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] " + hello);
            } catch (Exception e) {
                e.printStackTrace();
            }
            int time=new Random().nextInt(11)+5;
           TimeUnit.SECONDS.sleep(time);
        }
	}
	public DemoService getDemoService() {
		return demoService;
	}
	public void setDemoService(DemoService demoService) {
		this.demoService = demoService;
	}
	public HelloService getService() {
		return service;
	}
	public void setService(HelloService service) {
		this.service = service;
	}
	
}
