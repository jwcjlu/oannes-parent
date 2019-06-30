package com.jwcjlu.oannes.filter.support;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.oannes.common.Invocation;
import com.oannes.common.Invoker;
import com.oannes.common.Result;


/**
 * <pre>
 * 
 *  File: OannesHystrixCommand.java
 * 
 *  Copyright (c) 2017, globalegrow.com All Rights Reserved.
 * 
 *  Description:
 *  TODO
 * 
 *  Revision History
 *  Date,					Who,					What;
 *  2017年7月16日				jinwei				Initial.
 *
 * </pre>
 */
public class OannesHystrixCommand extends HystrixCommand<Result>{
	 private static final int DEFAULT_THREADPOOL_CORE_SIZE = 30;
	 private Invoker<?>       invoker;
	 private Invocation       invocation;
	   public OannesHystrixCommand(Invoker<?> invoker,Invocation invocation){
	        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(invocation.getInterface().getName()))
	                    .andCommandKey(HystrixCommandKey.Factory.asKey(String.format("%s_%d", invocation.getMethod(),
	                                                                                 invocation.getAgrs() == null ? 0 : invocation.getAgrs().length)))
	              .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
	                                            .withCircuitBreakerRequestVolumeThreshold(20)//10秒钟内至少19此请求失败，熔断器才发挥起作用
	                                            .withCircuitBreakerSleepWindowInMilliseconds(30000)//熔断器中断请求30秒后会进入半打开状态,放部分流量过去重试
	                                            .withCircuitBreakerErrorThresholdPercentage(50)//错误率达到50开启熔断保护
	                                            .withExecutionTimeoutEnabled(false))//使用的超时，禁用这里的超时
	              .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(30)));//线程池为30
	       
	        
	        this.invoker=invoker;
	        this.invocation=invocation;
	    }
	@Override
	protected Result run() throws Exception {
		// TODO Auto-generated method stub
		return invoker.invoke(invocation);
	}

}

