package com.jwcjlu.oannes.transport.futrue;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import com.jwcjlu.oannes.transport.excption.RemoteException;
import com.oannes.common.ResultType;
import com.oannes.common.RpcRequest;
import com.oannes.common.RpcResponse;

public class DefaultResponseFuture implements ResponseFuture{
	private final static long defaultTimeOut=10000;
	private final static ConcurrentHashMap<String,ResponseFuture>responseFutures=new ConcurrentHashMap<String,ResponseFuture>();
	private ReentrantLock  lock=new ReentrantLock();
	private Condition done=lock.newCondition();
	private boolean isDone=false;
	private CompletableFuture future;
	public DefaultResponseFuture(RpcRequest request, CompletableFuture future){
		responseFutures.put(request.getId(), this);
		this.future=future;
	}
	public  void receive(RpcResponse response) {
		// TODO Auto-generated method stub
		lock.lock();
		try{
			handleResponse(response);
			responseFutures.remove(response.getId());
		}finally{
			lock.unlock();
			
		}
		
	}
	private void handleResponse(RpcResponse response) {
		if(response.getCode()== ResultType.FAIL){
			future.completeExceptionally(new Exception(response.getResult().toString()));
		}else{
			future.complete(response.getResult());
		}
	}

	public static ConcurrentHashMap<String, ResponseFuture> getResponsefutures() {
		return responseFutures;
	}



}
