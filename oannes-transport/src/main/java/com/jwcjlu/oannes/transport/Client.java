package com.jwcjlu.oannes.transport;

import com.jwcjlu.oannes.transport.clientpool.RpcHandlerContent;
import com.jwcjlu.oannes.transport.futrue.ResponseFuture;
import com.oannes.common.RpcRequest;

public interface Client {
	
	void  close()throws RemoteException;
	
	boolean isConnected()throws RemoteException;
	
	void send(Object msg);
	
	ResponseFuture request(RpcRequest msg);


	void disconnect();

	void startReadTimeoutHandler();
	void startReadTimeoutHandler(long timeout);

}
