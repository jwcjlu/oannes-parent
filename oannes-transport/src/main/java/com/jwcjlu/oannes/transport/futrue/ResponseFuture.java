package com.jwcjlu.oannes.transport.futrue;

import com.jwcjlu.oannes.common.RpcResponse;
import com.jwcjlu.oannes.transport.RemoteException;

public interface ResponseFuture {
	
	Object get()throws RemoteException;
	
	Object get(long timeOut)throws RemoteException;
	
	boolean isDone();
	
	public  void receive(RpcResponse reponse);
	

}
