package com.jwcjlu.oannes.config;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;

import com.jwcjlu.oannes.common.RpcRequest;
import com.jwcjlu.oannes.common.RpcResponse;

public class ServiceBean<T> extends ConfigBean implements InitializingBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ApplicationContext context;
	public ServiceBean(ApplicationContext context){
		this.context=context;
	}
	public ApplicationContext getContext() {
		return context;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		lock.lock();
		try{
			String key=getHost()+":"+getPort();
			if(!services.contains(key)){
				export();
			}
		}finally{
			lock.unlock();
		}
		
	}
	@SuppressWarnings("unchecked")
	private void export() throws IOException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		ServerSocket  ss=new ServerSocket();
		SocketAddress endpoint=new InetSocketAddress(getHost(), getPort());
		ss.bind(endpoint);
		while(true){
			 Socket s=	ss.accept();
			 ObjectInputStream ois= new ObjectInputStream( s.getInputStream());
			 RpcRequest req=(RpcRequest) ois.readObject();
			 Object obj=context.getBean(getInterfaces());
			 Method method= obj.getClass().getDeclaredMethod(req.getMethod(), req.getParameterTypes());
			 Object result=method.invoke(obj, req.getArgs());
			 RpcResponse res=new RpcResponse();
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
