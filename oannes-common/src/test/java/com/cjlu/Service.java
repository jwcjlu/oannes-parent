package com.cjlu;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Service {
	private static Map<Class<?>,Object> services=new HashMap<Class<?>,Object>();
	public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		services.put(Calculator.class, new CalculatorImpl());
		ServerSocket  ss=new ServerSocket(8888);
		while(true){
			 Socket s=	ss.accept();
			 ObjectInputStream ois= new ObjectInputStream( s.getInputStream());
			 Request req=(Request) ois.readObject();
			 Object obj=services.get(req.getType());
			 Method method= obj.getClass().getDeclaredMethod(req.getMethod(), req.getParameterTypes());
			 Object result=method.invoke(obj, req.getArgs());
			 Response res=new Response();
			 res.setCode(1000);
			 res.setResult(result);
			 ObjectOutputStream output=new ObjectOutputStream(s.getOutputStream()) ;
			 output.writeObject(res);
			 output.flush();
			 ois.close();
			 output.close();
			 s.close();
		 
		}
	}

}
