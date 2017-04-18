package com.jwcjlu.oannes.register;

import java.util.List;

import com.jwcjlu.oannes.common.Handler;
import com.jwcjlu.oannes.register.listener.NotifyListener;

public class OannesListener<T> implements NotifyListener<T> {
	private Class<T> type;
	public OannesListener(Class<T> t){
		this.type=t;
	}

	@Override
	public void handleChildChange(String parentPath, List<String> currentChilds)
			throws Exception {
		// TODO Auto-generated method stub
			// TODO Auto-generated method stub
			Handler.updateHostAndPort(currentChilds, type);
			
		
	}

}
