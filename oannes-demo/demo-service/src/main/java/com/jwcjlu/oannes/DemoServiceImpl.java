package com.jwcjlu.oannes;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.jwcjlu.oannes.config.OannService;

@OannService(interfaces=DemoService.class,host="10.10.152.208")
public class DemoServiceImpl implements DemoService {
	@Override
	public String sayHello(String msg) {
		// TODO Auto-generated method stub
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + msg + ", request from consumer: " );

		return "response return ["+msg+"]";
	}

}
