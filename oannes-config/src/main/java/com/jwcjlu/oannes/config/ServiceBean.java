package com.jwcjlu.oannes.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;

import com.jwcjlu.oannes.common.spring.SpringBeanUtils;
import com.jwcjlu.oannes.rpc.protocol.OannesProtocol;
import com.oannes.common.URL;

public class ServiceBean<T> extends ConfigBean implements InitializingBean{
	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	private static ApplicationContext context;
	@SuppressWarnings("static-access")
	public ServiceBean(ApplicationContext context){
		this.context=context;
	}
	public ApplicationContext getContext() {
		return context;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		 RegisterBean bean=SpringBeanUtils.getBean(RegisterBean.class);
	     URL url =builderURL();
	     url.setBackupAddress(bean.getString("register"));
	     context.getBean(OannesProtocol.class).export(url);
	}


	

}
