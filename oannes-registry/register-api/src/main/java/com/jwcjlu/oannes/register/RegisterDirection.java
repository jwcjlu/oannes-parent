package com.jwcjlu.oannes.register;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.util.CollectionUtils;

import com.jwcjlu.oannes.cluster.ClusterFactory;
import com.jwcjlu.oannes.cluster.loadBalance.LoadBalanceFactory;
import com.jwcjlu.oannes.common.spring.SpringBeanUtils;
import com.jwcjlu.oannes.register.listener.NotifyListener;
import com.oannes.common.Invocation;
import com.oannes.common.Invoker;
import com.oannes.common.Protocol;
import com.oannes.common.URL;
import com.oannes.common.exception.RpcException;

public class RegisterDirection implements NotifyListener,Direction{
	@SuppressWarnings("rawtypes")
	private ConcurrentMap<String, List<Invoker>> invokerMap=new ConcurrentHashMap<String, List<Invoker>>();
	private Register register;
	@Override
	public void notify(List<URL> urls) {
		// TODO Auto-generated method stub
		refrashInvoker(urls);
	}

	public Register getRegister() {
		return register;
	}

	public void setRegister(Register register) {
		this.register = register;
	}
	

	@SuppressWarnings({ "rawtypes" })
	void refrashInvoker(List<URL> urls){
		if(CollectionUtils.isEmpty(urls)){
			return ;
		}
		Protocol protocol=SpringBeanUtils.getBean(Protocol.class);
		for(URL url:urls){
			try {
				Invoker invoker = (Invoker) protocol.refer(url);
				String key=buildKey(url);
				List<Invoker> invokers=invokerMap.get(key);
				if(CollectionUtils.isEmpty(invokers)){
					List<Invoker>is=new ArrayList<Invoker>();
					invokerMap.put(key,is);
					invokers=invokerMap.get(key);
				}
				invokers.add(invoker);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Invoker lookUp(URL url,Invocation invocation) throws RpcException {
		// TODO Auto-generated method stub
		List<Invoker>invokers=invokerMap.get(buildKey(url));
		ClusterFactory cf=SpringBeanUtils.getBean(ClusterFactory.class);
		LoadBalanceFactory lbf=SpringBeanUtils.getBean(LoadBalanceFactory.class);
		String clusterName=url.getParameter("cluster");
		String loadBalanceName=url.getParameter("loadBalance");
		return cf.getCluster(clusterName).select(invokers, lbf.getLoadBalance(loadBalanceName), invocation, url);
	}
	private String buildKey(URL url){
		return url.getServiceInterface();
	}
	

}
