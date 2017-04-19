package com.jwcjlu.oannes.register;

import java.util.List;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jwcjlu.oannes.common.Handler;
import com.jwcjlu.oannes.register.listener.NotifyListener;
import com.jwcjlu.oannes.transport.ProxyHandler;

public class OannesListener<T> implements NotifyListener<T> {
	private Logger log=LoggerFactory.getLogger(OannesListener.class);
	private Class<T> type;
	public OannesListener(Class<T> t){
		this.type=t;
	}

	@Override
	public void handleChildChange(String parentPath, List<String> currentChilds)
			throws Exception {
		// TODO Auto-generated method stub
			// TODO Auto-generated method stub
		    log.info(type+"更新为：【"+currentChilds+"】");
			ProxyHandler.updateHostAndPort(currentChilds, type);
			
		
	}

}
