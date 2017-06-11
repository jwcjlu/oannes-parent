package com.jwcjlu.oannes.rpc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.jwcjlu.oannes.register.Direction;
import com.jwcjlu.oannes.register.RegisterDirection;
import com.oannes.common.Invocation;
import com.oannes.common.Invoker;
import com.oannes.common.RpcInvocation;
import com.oannes.common.URL;
import com.oannes.common.exception.RpcException;
public class JdkProxyHandler<T> implements InvocationHandler, com.jwcjlu.oannes.rpc.proxy.ProxyHandler<T> {
	private Direction direction;
	private URL url;

	private Class<T> type;

	public JdkProxyHandler(Class<T> type,Direction direction,URL url) {
		this.type = type;
		this.direction=direction;
		this.url=url;
	}
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Invocation  invocation =new RpcInvocation(method,args,type);
		Invoker<T> invoker=direction.lookUp(url,invocation);
		if(invoker==null){
			System.out.println("no provider is usefull");
			throw new RpcException("no provider is usefull");
		}
		Object obj=null;
		try{
			obj=invoker.invoke(invocation).getObj();
		}catch(Exception e){
			throw e;
		}
		return obj;

	}



	

}