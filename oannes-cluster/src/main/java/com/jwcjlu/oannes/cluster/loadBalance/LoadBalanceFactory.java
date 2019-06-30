package com.jwcjlu.oannes.cluster.loadBalance;

import java.util.HashMap;
import java.util.Map;

import com.jwcjlu.oannes.common.services.BootService;
import org.springframework.stereotype.Component;

import com.oannes.common.LoadBalance;
import com.oannes.common.util.StringUtils;

/**
 * <pre>
 * 
 *  File: LoadBalanceFactory.java
 * 
 *  Copyright (c) 2017,jwcjlu.com All Rights Reserved.
 * 
 *  Description:
 *  TODO
 * 
 *  Revision History
 *  Date,					Who,					What;
 *  2017年6月11日				jinwei				Initial.
 *
 * </pre>
 */
@Component
public class LoadBalanceFactory implements BootService {
	  private  Map<String,LoadBalance> loadBalanceMap=new HashMap<String,LoadBalance>();
		public  LoadBalance getLoadBalance(String name){
			if(StringUtils.isNotEmpty(name)){
				 return loadBalanceMap.get(name);
			}else{
				return loadBalanceMap.get("default");
			}
		    
		}
		public  void registerLoadBalance(String name,LoadBalance loadBalance){
			loadBalanceMap.put(name, loadBalance);
		}
}

