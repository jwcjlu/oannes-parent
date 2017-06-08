package com.jwcjlu.oannes.common.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import com.oannes.common.Invocation;
import com.oannes.common.Invoker;
import com.oannes.common.RpcInvocation;
import com.oannes.common.URL;
import com.oannes.common.loadBalance.LoadBalance;
import com.oannes.common.loadBalance.RoundRobinLoadBalance;
public class JdkProxyHandler<T> implements InvocationHandler, com.jwcjlu.oannes.common.proxy.ProxyHandler<T> {
	private List<Invoker<T>>invokers=new ArrayList<Invoker<T>>(); 
	private LoadBalance loadBalance=new RoundRobinLoadBalance();
	private URL url;

	private Class<T> type;

	public JdkProxyHandler(Class<T> type) {
		this.type = type;
	}
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Invocation  invocation =new RpcInvocation(method,args,type);
		Invoker<T> invoker=loadBalance.select(invokers, url, invocation);
		return invoker.invoke(invocation);

	}

	@Override
	public void refrashInvoker(List<Invoker<T>> invokers) {
		// TODO Auto-generated method stub
		this.invokers=invokers;
	}
	@Override
	public List<Invoker<T>> getInvokers() {
		// TODO Auto-generated method stub
		return invokers;
	}
	@Override
	public void disponseInvoker(List<Invoker<T>> oldInvokers) {
		// TODO Auto-generated method stub
		
	}

	

}