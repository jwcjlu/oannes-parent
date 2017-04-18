package com.jwcjlu.oannes.register;

import java.io.IOException;
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
		zk.createPersistent(url.getPersisPath(), true);
		String []ephemerals=url.getPath().split("/");
		StringBuilder temp=new StringBuilder("");
		for(int i=0;i<ephemerals.length;i++){
			temp.append("/"+ephemerals[i]);
			zk.createEphemeral(url.getPersisPath()+temp.toString());
		}
		
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
	public  void  createPersistent(String path,boolean createParent){
		zk.createPersistent(path, createParent);
	}
	public static void main(String[] args) throws IOException {
		ZookeeperRegister zk = new ZookeeperRegister("192.168.1.125:2181");
		
		zk.createPersistent("/dubbo/oannes/ddd", true);
		System.in.read();
	}

}
