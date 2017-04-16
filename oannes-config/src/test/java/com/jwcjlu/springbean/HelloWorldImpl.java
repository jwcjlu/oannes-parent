package com.jwcjlu.springbean;

import com.jwcjlu.oannes.config.OannService;

@OannService(interfaces=HelloWorld.class)
public class HelloWorldImpl implements HelloWorld {

	@Override
	public String say(String msg) {
		// TODO Auto-generated method stub
		System.out.println(msg);
		return "response return["+msg+"]";
		
	}

}
