package com.jwcjlu.oannes.register;

import java.util.List;

import org.I0Itec.zkclient.ZkClient;

import com.jwcjlu.oannes.common.URL;
import com.jwcjlu.oannes.register.listener.NotifyListener;

public class ZookeeperRegister implements Register{
	
	 ZkClient zk;
  public ZookeeperRegister(String zkConfig){
	  zk = new ZkClient(zkConfig);
   }
	@Override
	public void register(URL url) {
		// TODO Auto-generated method stub
		zk.createEphemeral(url.getPath());
	}

	@Override
	public void unRegister(URL url) {
		// TODO Auto-generated method stub
		zk.delete(url.getPath());
	}

	@Override
	public void subscribe(List<URL> urls, NotifyListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unSubscribe(List<URL> urls, NotifyListener l) {
		// TODO Auto-generated method stub
		
	}

}
