package com.jwcjlu.oannes.register;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.jwcjlu.oannes.register.listener.NotifyListener;
import com.oannes.common.Constants;
import com.oannes.common.URL;

public class ZookeeperRegister extends FailbackRegistry {

	private ZkClient client;
	private static ZookeeperRegister register;
	private final static String DEFAULT_ROOT = "oannes";

	private final String root;
	private final ConcurrentMap<URL, ConcurrentMap<NotifyListener, ChildListener>> zkListeners = new ConcurrentHashMap<URL, ConcurrentMap<NotifyListener, ChildListener>>();

	public static ZookeeperRegister getInstance(URL url) {
		if (register != null) {
			return register;
		} else {
			register = new ZookeeperRegister(url);
		}
		return register;
	}

	private ZookeeperRegister(URL url) {
		super(url);

		root = Constants.PATH_SEPARATOR + url.getParameter("root", DEFAULT_ROOT);
		try {
			client = new ZkClientService(url);
			client.addListener(new StateListener() {
				@Override
				public void stateChanged(int connected) {
					// TODO Auto-generated method stub
					if (connected == StateListener.RECONNECTED) {
						try {
							recover();
						} catch (Exception e) {
						}
					}

				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}

	@Override
	public List<String> getChildForPath(String path) throws Exception {
		// TODO Auto-generated method stub
		return client.getChilder(path);
	}

	private String toCategoryPath(URL url) {
		return toServicePath(url) + Constants.PATH_SEPARATOR
				+ url.getParameter(Constants.CATEGORY_KEY, Constants.DEFAULT_CATEGORY);
	}

	private String toUrlPath(URL url) {
		return toCategoryPath(url) + Constants.PATH_SEPARATOR;
	}

	private String toServicePath(URL url) {
		String name = url.getServiceInterface();

		return toRootDir() + URL.encode(name);
	}
	private String toProviderPath(URL url){
		return toServicePath(url)+Constants.PATH_SEPARATOR+Constants.PROVIDERS_CATEGORY
				+Constants.PATH_SEPARATOR+URL.encode(url.getUrl());
	}
	private String toSubProviderPath(URL url){
		return toServicePath(url)+Constants.PATH_SEPARATOR+Constants.PROVIDERS_CATEGORY;
				
	}
	private String toConsummerPath(URL url){
		return toServicePath(url)+Constants.PATH_SEPARATOR+Constants.CONSUMERS_CATEGORY
				+Constants.PATH_SEPARATOR+url.getUrl();
				
	}

	private String toRootDir() {
		if (root.equals(Constants.PATH_SEPARATOR)) {
			return root;
		}
		return root + Constants.PATH_SEPARATOR;
	}

	@SuppressWarnings("unused")
	private String toRootPath() {
		return root;
	}

	@Override
	protected void doRegister(URL url) throws Exception {
		// TODO Auto-generated method stub
	
			client.createEphrmeralNode(toProviderPath(url), "");
		
	}

	@Override
	protected void doUnregister(URL url) throws Exception {
		// TODO Auto-generated method stub
		client.deleteNode(url.getPath());

	}

	@Override
	protected void doSubscribe(final URL url, final NotifyListener listener) throws RpcException {
		// TODO Auto-generated method stub
		try {

			List<URL> urls = new ArrayList<URL>();
		    String path=toSubProviderPath(url);
				ConcurrentMap<NotifyListener, ChildListener> listeners = zkListeners.get(url);
				if (listeners == null) {
					zkListeners.putIfAbsent(url, new ConcurrentHashMap<NotifyListener, ChildListener>());
					listeners = zkListeners.get(url);
				}
				ChildListener zkListener = listeners.get(listener);
				if (zkListener == null) {
					listeners.putIfAbsent(listener, new ChildListener() {
						public void childChanged(String parentPath, List<String> currentChilds) {
							ZookeeperRegister.this.notify(url, listener,
									toUrlsWithEmpty(url, parentPath, currentChilds));
						}
					});
					zkListener = listeners.get(listener);
				}
				//client.createEphrmeralNode(toConsummerPath(url), "");
				List<String> childrens = client.addChildListener(path, zkListener);
				if (childrens != null) {
					urls.addAll(toUrlsWithEmpty(url, path, childrens));
				}
			
			notify(url, listener, urls);

		} catch (Throwable e) {

			throw new RpcException(
					"Failed to subscribe " + url + " to zookeeper " + getUrl() + ", cause: " + e.getMessage(), e);

		}

	}

	private List<String> toCategoriesPath(URL url) {
		// TODO Auto-generated method stub
		return null;
	}

	private List<URL> toUrlsWithEmpty(URL url, String path, List<String> children) {
		// TODO Auto-generated method stub
		List<URL> urls=new ArrayList<URL>();
		for(String value:children){
			URL u=URL.valueOf(URL.decode(value)); 
			boolean added=true;
			for(Entry<String,String> entry:u.getParameters().entrySet()){
			    if(url.getParameter(entry.getKey())!=null&&!url.getParameter(entry.getKey()).equals(entry.getValue())){
			        added=false;
			        break;
			    }
			    
			}
			if(added){			    
			    urls.add(u);
			}
			
		}
		return urls;
	}

	@Override
	protected void doUnsubscribe(URL url, NotifyListener listener) {
		// TODO Auto-generated method stub
		  ConcurrentMap<NotifyListener, ChildListener> listeners = zkListeners.get(url);
	        if (listeners != null) {
	            ChildListener zkListener = listeners.get(listener);
	            if (zkListener != null) {
	                client.removeChildListener(toUrlPath(url), zkListener);
	            }
	        }
	}
}
