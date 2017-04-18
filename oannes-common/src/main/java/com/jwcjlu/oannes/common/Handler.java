package com.jwcjlu.oannes.common;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;

public class Handler implements InvocationHandler{
	private  String  ip;
	private int port;
	private Class<?> type;
	public Handler(Class<?> type,String ip,int port){
		this.type=type;
		this.ip=ip;
		this.port=port;
	}
	

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		Socket s=new Socket(ip,port);
		RpcRequest  req=new RpcRequest();
		req.setArgs(args);
		req.setMethod(method.getName());
		req.setParameterTypes(method.getParameterTypes());
		req.setType(type);
		 s.getOutputStream();
		 ObjectOutputStream output=new ObjectOutputStream(s.getOutputStream()) ;
		 output.writeObject(req);
		 output.flush();
		 ObjectInputStream ois= new ObjectInputStream( s.getInputStream());
		 RpcResponse res= (RpcResponse) ois.readObject();
		 output.close();
		 ois.close();
		 s.close();
	
		return res.getResult();
		
	}

}
