package com.jwcjlu.oannes.transport.exchange;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import com.jwcjlu.oannes.transport.excption.RemoteException;
import com.jwcjlu.oannes.transport.clientpool.ClientPool;
import com.jwcjlu.oannes.transport.clientpool.ClientPoolManager;
import com.jwcjlu.oannes.transport.futrue.ResponseFuture;
import com.oannes.common.Invocation;
import com.oannes.common.RpcRequest;
import com.oannes.common.URL;

public class ExchangeClientImpl implements ExchangeClient {
	private ClientPool clientPool;

	public ExchangeClientImpl(){

	}

	@Override
	public void connect(URL url) throws RemoteException  {
		// TODO Auto-generated method stub
		clientPool=ClientPoolManager.INSTANCE.getClientPool(url);

	}

	@Override
	public Object request(Invocation invocation) throws  Exception {
		// TODO Auto-generated method stub
		RpcRequest  req=new RpcRequest();
		req.setId(UUID.randomUUID().toString());
		req.setArgs(invocation.getAgrs());
		req.setMethod(invocation.getMethod().getName());
		req.setParameterTypes(invocation.getMethod().getParameterTypes());
		req.setType(invocation.getInterface());
		req.setAsyn(invocation.isAsyn());
		CompletableFuture<Object> result=new CompletableFuture<>();
		clientPool.borrowObject().request(req,result);
		if(invocation.isAsyn()){
			return result;
		}else{
			return result.get();
		}


	}


}
