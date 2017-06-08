package com.jwcjlu.oannes.config;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.jwcjlu.oannes.register.Register;
import com.jwcjlu.oannes.register.ZookeeperRegister;
import com.jwcjlu.oannes.transport.RemoteException;
import com.jwcjlu.oannes.transport.exchange.ExchangeClient;
import com.jwcjlu.oannes.transport.exchange.ExchangeClientImpl;
import com.jwcjlu.oannes.transport.exchange.ExchangeServerImpl;
import com.oannes.common.Invoker;
import com.oannes.common.URL;
import com.oannes.common.threadpool.NamedThreadFactory;

@Service
public class OannesProtocol {
	private volatile  boolean isServer=false;
	private static final ExecutorService  service=Executors.newCachedThreadPool(new NamedThreadFactory("oannesProtocol", true));
	public void export(URL url){
		Register  register=ZookeeperRegister.getInstance(url);
		try {
			register.register(url);
			if(!isServer){
				isServer=true;
				openServer(url);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@SuppressWarnings("rawtypes")
	public Object refer(final URL url) throws InterruptedException{
		CountDownLatch  latch=new CountDownLatch(1);
		final ExchangeClient client=new ExchangeClientImpl(latch);
		service.submit(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					client.connect(url);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		latch.await(3,TimeUnit.SECONDS);
	    Invoker invoker=new OannesInvoker(url,client);
	    return  invoker;
	}
	void openServer(final URL url){
		service.execute(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				new ExchangeServerImpl().bind(url);
			}
			
		});
		
	}

}
