package com.jwcjlu.oannes.rpc.proxy;

import java.lang.reflect.InvocationHandler;

import org.springframework.stereotype.Service;

import com.jwcjlu.oannes.rpc.proxy.AbstractProxy;
import com.jwcjlu.oannes.rpc.proxy.ProxyHandler;
import com.oannes.common.Constants;
@Service
public class JdkProxy extends AbstractProxy{

	@SuppressWarnings({ "unchecked", "rawtypes" })
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
