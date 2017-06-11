package com.jwcjlu.oannes.cluster;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.oannes.common.util.StringUtils;

/**
 * <pre>
 * 
 *  File: ClusterFactory.java
 * 
 *  Copyright (c) 2017, globalegrow.com All Rights Reserved.
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
public class ClusterFactory {
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

