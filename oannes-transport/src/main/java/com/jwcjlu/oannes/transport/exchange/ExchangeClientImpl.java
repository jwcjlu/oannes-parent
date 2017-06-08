package com.jwcjlu.oannes.transport.exchange;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import com.jwcjlu.oannes.transport.Client;
import com.jwcjlu.oannes.transport.NettyClient;
import com.jwcjlu.oannes.transport.RemoteException;
import com.oannes.common.Invocation;
import com.oannes.common.RpcRequest;
import com.oannes.common.RpcResponse;
import com.oannes.common.URL;

public class ExchangeClientImpl implements ExchangeClient {
	private CountDownLatch latch;
	private Client client;
	public ExchangeClientImpl(CountDownLatch latch){
		this.latch=latch;
	}

	@Override
	public void connect(URL url) throws RemoteException  {
		// TODO Auto-generated method stub
		client=new NettyClient(url.getHost(),url.getPort(),this);
		client.connect();
	}

	@Override
	public Object request(Invocation invocation) throws RemoteException {
		// TODO Auto-generated method stub

		RpcRequest  req=new RpcRequest();
		req.setId(UUID.randomUUID().toString());
		req.setArgs(invocation.getAgrs());
		req.setMethod(invocation.getMethod().getName());
		req.setParameterTypes(invocation.getMethod().getParameterTypes());
		req.setType(invocation.getInterface());

		if(client==null){
			System.out.println(" no user provider");
			return null;
		}

		return client.request(req).get();
	}
	@Override
	public void countDown(){
		latch.countDown();
	}

}
