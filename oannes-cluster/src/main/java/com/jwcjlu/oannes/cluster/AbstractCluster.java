package com.jwcjlu.oannes.cluster;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;



/**
 * <pre>
 * 
 *  File: AbstractCluster.java
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
public abstract class AbstractCluster implements Cluster{
	@Resource
	private ClusterFactory clusterFactory;
	@Override
	@PostConstruct
	public void registerFactory() {
		// TODO Auto-generated method stub
		clusterFactory.registerCluster(getName(), this);
	}

}

