package com.jwcjlu.oannes.filter;

import com.oannes.common.Invocation;
import com.oannes.common.Invoker;
import com.oannes.common.Result;
import com.oannes.common.exception.RpcException;

public class MetricsFilter implements Filter{

	@Override
	public Result invoke(Invoker invoker, Invocation invocation) throws RpcException {
		// TODO Auto-generated method stub
		SpringMetricsBean.metric();
        Result result=invoker.invoke(invocation);
        return result;
	}

}
