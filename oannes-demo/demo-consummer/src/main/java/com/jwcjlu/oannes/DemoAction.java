package com.jwcjlu.oannes;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.jwcjlu.oannes.config.OannConsumer;

@Service
public class DemoAction {
	@OannConsumer(interfaces=DemoService.class)
	private DemoService demoService;
	public void start() throws Exception {
        for (int i = 0; i < Integer.MAX_VALUE; i ++) {
            try {
            	String hello = demoService.sayHello("world" + i);
                System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] " + hello);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Thread.sleep(6000);
        }
	}
	public DemoService getDemoService() {
		return demoService;
	}
	public void setDemoService(DemoService demoService) {
		this.demoService = demoService;
	}
	
}
