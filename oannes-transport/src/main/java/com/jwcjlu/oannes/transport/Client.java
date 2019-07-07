package com.jwcjlu.oannes.transport;

import com.jwcjlu.oannes.transport.excption.RemoteException;
import com.jwcjlu.oannes.transport.futrue.ResponseFuture;
import com.oannes.common.RpcRequest;

import java.util.concurrent.CompletableFuture;

public interface Client {
	
	void  close()throws RemoteException;
	
	boolean isConnected()throws RemoteException;
	
	void send(Object msg);
	
	ResponseFuture request(RpcRequest msg, CompletableFuture future);


	void disconnect();

	void startReadTimeoutHandler();
	void startReadTimeoutHandler(long timeout);

}
