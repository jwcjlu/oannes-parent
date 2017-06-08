package com.jwcjlu.oannes.config;

import java.util.UUID;

import com.jwcjlu.oannes.common.proxy.JdkProxyHandler;
import com.jwcjlu.oannes.transport.RemoteException;
import com.jwcjlu.oannes.transport.exchange.ExchangeClient;
import com.oannes.common.Invocation;
import com.oannes.common.Invoker;
import com.oannes.common.RpcRequest;
import com.oannes.common.URL;

public class OannesInvoker implements Invoker{
	private URL url;
	private ExchangeClient client;
	public OannesInvoker(URL url,ExchangeClient client){
		this.url=url;
		this.client=client;
	}

	@Override
	public Object invoke(Invocation invocation) {
		// TODO Auto-generated method stub
		RpcRequest request=new RpcRequest();
		request.setArgs(invocation.getAgrs());
		request.setId(UUID.randomUUID().toString());
		request.setMethod(invocation.getMethod().getName());
		request.setType(invocation.getInterface());
		Object obj=null;
		try{
          obj=client.request(invocation);
		}catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public URL getURL() {
		// TODO Auto-generated method stub
		return url;
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj==null||! (obj instanceof JdkProxyHandler)){
			return false;
		}
		return this.getURL().equals(((Invoker)obj).getURL());
	}
}
