package com.jwcjlu.oannes.register;

import java.util.List;

import com.jwcjlu.oannes.register.listener.NotifyListener;
import com.oannes.common.URL;

public interface Register {
	void register(URL url) throws Exception;
	
	void unRegister(URL url) throws Exception;
	
	void subscribe(List<URL> urls,NotifyListener l);
	
	void unSubscribe(List<URL> urls,NotifyListener l);
	 
	void subscribe(URL url,NotifyListener l);
	
	List<String> getChildForPath(String path) throws Exception;
	void destroy();
	

}
