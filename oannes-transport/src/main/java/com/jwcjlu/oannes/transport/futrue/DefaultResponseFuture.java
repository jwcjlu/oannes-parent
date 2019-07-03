package com.jwcjlu.oannes.transport.futrue;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import com.jwcjlu.oannes.transport.RemoteException;
import com.oannes.common.RpcRequest;
import com.oannes.common.RpcResponse;

public class DefaultResponseFuture implements ResponseFuture{
	private final static long defaultTimeOut=10000;
	private final static ConcurrentHashMap<String,ResponseFuture>responseFutures=new ConcurrentHashMap<String,ResponseFuture>();
	private ReentrantLock  lock=new ReentrantLock();
	private Condition done=lock.newCondition();
	private RpcResponse reponse;
	private boolean isDone=false;
	public DefaultResponseFuture(RpcRequest request){
		responseFutures.put(request.getId(), this);
	}
	@Override
	public Object get() throws RemoteException{
		// TODO Auto-generated method stub
		return get(defaultTimeOut);
	}

	@Override
	public Object get(long timeOut) throws RemoteException {
		// TODO Auto-generated method stub
		lock.lock();
		try{
			if(!isDone()){
				
				done.await(timeOut,TimeUnit.MILLISECONDS);
			}
		}catch(Exception e){
			
		}finally{
			lock.unlock();
		}
		return fromReturnToResponse();
	}

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return isDone;
	}
	private Object  fromReturnToResponse() throws RemoteException{
		if(reponse==null){
			throw new RemoteException("dispatch failure!!!");
		}
		return reponse.getResult();
	}


	public  void receive(RpcResponse reponse) {
		// TODO Auto-generated method stub
		lock.lock();
		try{
			this.reponse=reponse;
			isDone=true;
			responseFutures.remove(reponse.getId());
			done.signal();
		}finally{
			lock.unlock();
			
		}
		
	}
	public static ConcurrentHashMap<String, ResponseFuture> getResponsefutures() {
		return responseFutures;
	}



}
