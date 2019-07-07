package com.jwcjlu.oannes.transport.futrue;

import com.jwcjlu.oannes.transport.excption.RemoteException;
import com.oannes.common.RpcResponse;

public interface ResponseFuture {
/*
	Object get()throws RemoteException;
	
	Object get(long timeOut)throws RemoteException;
	
	boolean isDone();*/
	
	void receive(RpcResponse reponse);
	

}
