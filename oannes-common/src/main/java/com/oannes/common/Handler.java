package com.oannes.common;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class Handler implements InvocationHandler{
	private static Map<Class<?>,Queue<String>> hostAndPort=new ConcurrentHashMap<>(100);
	private Class<?> type;
	public Handler(Class<?> type){
		this.type=type;
	}
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		String hostAndPort=selectHostAndPort(type);
		String []agrs=hostAndPort.split(":");
		Socket s=new Socket(agrs[0],Integer.valueOf(agrs[1]));
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
	private String selectHostAndPort(Class<?> type){
		Queue<String> hostAndPortQ =hostAndPort.get(type);	
		String hostAndPort=hostAndPortQ.remove();
		hostAndPortQ.add(hostAndPort);
		return hostAndPort;
		
	}
	public static void  updateHostAndPort(List<String> hostAndPorts,Class<?>type){
		hostAndPort.put(type, listToQueue(hostAndPorts));	
	}
	private static Queue<String> listToQueue(List<String> hostAndPorts){
		Queue<String>  q=new LinkedBlockingQueue<String>();
		for(String hap:hostAndPorts){
			q.add(hap);
		}
		return q;
		
	}

}
