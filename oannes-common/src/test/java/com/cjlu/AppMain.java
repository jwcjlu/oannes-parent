package com.cjlu;

import java.lang.reflect.Proxy;

public class AppMain {
	public static void main(String[] args) {
		 Class<?>[] interfaces =new Class<?>[]{Calculator.class};
		 Calculator  cal=	(Calculator) Proxy.newProxyInstance(Calculator.class.getClassLoader(),interfaces, new Handler(Calculator.class));
		 System.out.println(cal.add(1, 2));
		 System.out.println(cal.reduce(100, 80));
	}

}
