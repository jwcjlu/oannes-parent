package com.jwcjlu.oannes;

import java.io.IOException;
import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

public class ZkTest {
	public static void main(String[] args) {
	   
	        ZkClient zkClient = new ZkClient("127.0.0.1:2181");
	        zkClient.subscribeChildChanges("/oannes/com.jwcjlu.oannes.HelloService/providers", new IZkChildListener() {

				@Override
				public void handleChildChange(String parentPath,
						List<String> currentChilds) throws Exception {
					// TODO Auto-generated method stub
					System.out.println(parentPath);
					System.out.println(currentChilds);
					
				}
	          

		
	        });
	        zkClient.subscribeChildChanges("/oannes/com.jwcjlu.oannes.DemoService/providers", new IZkChildListener() {

				@Override
				public void handleChildChange(String parentPath,
						List<String> currentChilds) throws Exception {
					// TODO Auto-generated method stub
					System.out.println(parentPath);
					System.out.println(currentChilds);
					
				}
		
	        });

	        try {
	        	System.out.println("dsfsdf");
	            System.in.read();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    
	}

}
