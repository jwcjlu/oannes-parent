package com.jwcjlu.oannes.transport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

import com.jwcjlu.oannes.common.RpcRequest;

public class ProxyHandler implements InvocationHandler{

private  static AtomicInteger  incrementGet=new AtomicInteger(0); 
private static volatile List<Client> clients=new Vector<Client>(100);
private Class<?> type;
public ProxyHandler(Class<?> type){
	this.type=type;
}
public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
	// TODO Auto-generated method stub
	
	RpcRequest  req=new RpcRequest();
	req.setId(UUID.randomUUID().toString());
	req.setArgs(args);
	req.setMethod(method.getName());
	req.setParameterTypes(method.getParameterTypes());
	req.setType(type);
	Client client=selectClient();
	if(client==null){
		System.out.println(" no user provider");
		return null;
	}

	return client.request(req).get();
	
}
   private Client selectClient(){
	   if(clients.size()==0){
		   return null;
	   }

	return clients.get(incrementGet.incrementAndGet()%clients.size());
	
}
public synchronized static void  updateHostAndPort(List<String> hostAndPorts,Class<?>type){
	List<Client>removeClient=new ArrayList<Client>();
	for(Client c:clients){
		if(hostAndPorts.contains(c.getKey())){
			hostAndPorts.remove(c.getKey());
		}else{
			removeClient.add(c);
			try {
				c.close();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
			}
		}
	}
	clients.removeAll(removeClient);
	for(String hostAndPort:hostAndPorts){
		addClient(hostAndPort);
	
		
	}
	
}
public static void addClient(String hostAndPort){
	
	 String []hap=hostAndPort.split(":");
	// TODO Auto-generated method stub
	final Client client=new NettyClient(hap[0],Integer.valueOf(hap[1]));
	clients.add(client);
	System.out.println(hostAndPort+"[client  is ready  start!]");

	new Thread(new Runnable(){
		@Override
		public void run() {
			try {
				client.connect();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}).start();
	
}


	
}