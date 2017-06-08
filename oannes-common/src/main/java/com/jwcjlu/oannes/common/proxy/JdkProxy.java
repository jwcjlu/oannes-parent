package com.jwcjlu.oannes.common.proxy;

import java.lang.reflect.InvocationHandler;

import org.springframework.stereotype.Service;

import com.oannes.common.Constants;
@Service
public class JdkProxy extends AbstractProxy{

	@SuppressWarnings("unchecked")
	@Override
	public <T> T newProxyInstance(ClassLoader loader, Class<?>[] interfaces, ProxyHandler handler) {
	
		return (T) java.lang.reflect.Proxy.newProxyInstance(loader, interfaces, (InvocationHandler)handler);
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return Constants.DEFAULT_PROXY;
	}

}
