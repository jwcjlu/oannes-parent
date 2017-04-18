package com.jwcjlu.oannes;

import com.jwcjlu.oannes.config.OannService;

@OannService(interfaces=HelloService.class)
public class HelloServiceImpl implements HelloService {

	@Override
	public void sayHello() {
		// TODO Auto-generated method stub
		System.out.println("say hello!");
	}

}
