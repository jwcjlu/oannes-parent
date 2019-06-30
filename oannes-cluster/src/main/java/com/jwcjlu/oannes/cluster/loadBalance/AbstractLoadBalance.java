package com.jwcjlu.oannes.cluster.loadBalance;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.jwcjlu.oannes.common.services.BootServiceManager;
import com.oannes.common.Invocation;
import com.oannes.common.Invoker;
import com.oannes.common.LoadBalance;
import com.oannes.common.exception.RpcException;

public abstract class AbstractLoadBalance implements LoadBalance{
	private LoadBalanceFactory loadBalanceFactory;
	@Override
	public void registerFactory() {
		// TODO Auto-generated method stub
		loadBalanceFactory.registerLoadBalance(getName(), this);
	}
	@SuppressWarnings("rawtypes")
	public  Invoker select(List<Invoker> invokers, Invocation invocation) throws RpcException{
		   if (invokers == null || invokers.size() == 0)
	            return null;
	        if (invokers.size() == 1)
	            return invokers.get(0);
	        return doSelect(invokers, invocation);
	}
	@SuppressWarnings("rawtypes")
	public abstract  Invoker doSelect(List<Invoker> invokers, Invocation invocation);

	@Override
	public void onComplete() {
		loadBalanceFactory= BootServiceManager.INSTANCE.findBootService(LoadBalanceFactory.class);
		registerFactory();
	}
}
