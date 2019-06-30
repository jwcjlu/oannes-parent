package com.jwcjlu.oannes.cluster;

import com.jwcjlu.oannes.common.services.BootService;
import com.oannes.common.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 
 *  File: ClusterFactory.java
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

public class ClusterFactory implements BootService {
    private  Map<String,Cluster> clusterMap=new HashMap<String,Cluster>();
	public  Cluster getCluster(String name){
		if(StringUtils.isNotEmpty(name)){
			 return clusterMap.get(name);
		}else{
			return clusterMap.get("default");
		}
	    
	}
	public  void registerCluster(String name,Cluster cluster){
		clusterMap.put(name, cluster);
	}

}

