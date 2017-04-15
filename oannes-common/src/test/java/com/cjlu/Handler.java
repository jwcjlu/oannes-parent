package com.cjlu;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;

public class Handler implements InvocationHandler{
	private Class<?> type;
	public Handler(Class<?> type){
		this.type=type;
	}
	

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		Socket s=new Socket("localhost",8888);
		Request  req=new Request();
		req.setArgs(args);
		req.setMethod(method.getName());
		req.setParameterTypes(method.getParameterTypes());
		req.setType(type);
		s.getOutputStream();
		 ObjectOutputStream output=new ObjectOutputStream(s.getOutputStream()) ;
		 output.writeObject(req);
		 output.flush();
		 ObjectInputStream ois= new ObjectInputStream( s.getInputStream());
		Response res= (Response) ois.readObject();
		 output.close();
		 ois.close();
		 s.close();
	
		return res.getResult();
		
	}

}
