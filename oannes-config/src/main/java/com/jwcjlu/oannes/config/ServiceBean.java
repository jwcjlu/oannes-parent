package com.jwcjlu.oannes.config;

import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.jwcjlu.oannes.common.spring.SpringBeanUtils;
import com.jwcjlu.oannes.rpc.protocol.OannesProtocol;
import com.oannes.common.URL;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
@Setter
public class ServiceBean<T> extends ConfigBean implements InitializingBean, EnvironmentAware {
	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	private static ApplicationContext context;
	private OannService oannService;
	@SuppressWarnings("static-access")
	public ServiceBean(ApplicationContext context){
		this.context=context;
	}
	public ApplicationContext getContext() {
		return context;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		setter(oannService);
		URL url =builderURL();
		OannesProtocol.getInstance().export(url);
	}



	

}
