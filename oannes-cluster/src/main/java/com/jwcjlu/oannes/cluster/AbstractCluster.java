package com.jwcjlu.oannes.cluster;

import com.jwcjlu.oannes.common.services.BootServiceManager;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;


/**
 * <pre>
 * 
 *  File: AbstractCluster.java
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
public abstract class AbstractCluster implements Cluster{

	private ClusterFactory clusterFactory;
	@Override
	public void registerFactory() {
		// TODO Auto-generated method stub
		clusterFactory.registerCluster(getName(), this);
	}

	@Override
	public void onComplete() {
		clusterFactory= BootServiceManager.INSTANCE.findBootService(ClusterFactory.class);
		registerFactory();
	}
}

