package com.jwcjlu.oannes.transport;

import com.jwcjlu.oannes.transport.futrue.ResponseFuture;
import com.oannes.common.RpcRequest;

public interface Client {
	void connect()throws RemoteException;
	
	void reconnect()throws RemoteException;
	
	void  close()throws RemoteException;
	
	boolean isConnected()throws RemoteException;
	
	boolean isClose()throws RemoteException;
	
	void send(Object msg);
	
	ResponseFuture request(RpcRequest msg);
	
	String getKey();
}
