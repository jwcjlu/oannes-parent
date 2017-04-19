package com.jwcjlu.oannes.register;

import java.util.List;

import com.jwcjlu.oannes.common.URL;
import com.jwcjlu.oannes.register.listener.NotifyListener;

public interface Register {
	void register(URL url);
	
	void unRegister(URL url);
	
	void subscribe(List<URL> urls,NotifyListener l);
	
	void unSubscribe(List<URL> urls,NotifyListener l);
	 
	void subscribe(String subject,NotifyListener l,Class type);
	
	List<String> getChildForPath(String path);

}
