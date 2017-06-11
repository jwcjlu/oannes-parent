package com.jwcjlu.oannes.rpc.proxy;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.jwcjlu.oannes.rpc.proxy.Proxy;
import com.oannes.common.Constants;
import com.oannes.common.URL;

@Service
public class ProxyFactory {
	private Map<String,Proxy> proxys=new HashMap<String,Proxy>();
	public Proxy getProxy(URL url){
		String key=url.getParameter(Constants.PROXY,Constants.DEFAULT_PROXY);
		return proxys.get(key); 
	}
	public Proxy getProxy(){
		return proxys.get(Constants.DEFAULT_PROXY); 
	}
	public void registerProxy(String key,Proxy proxy){
		proxys.put(key, proxy);
	}

}
