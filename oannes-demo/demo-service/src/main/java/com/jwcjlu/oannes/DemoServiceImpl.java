package com.jwcjlu.oannes;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.jwcjlu.oannes.config.OannService;
import com.oannes.common.NetUtil;

@OannService(interfaces=DemoService.class)
public class DemoServiceImpl implements DemoService {
	@Override
	public String sayHello(String msg) {
		// TODO Auto-generated method stub
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + msg  );

		return "response return ["+msg+"] request from provider: "+NetUtil.getRemoteAddress();
	}

}
