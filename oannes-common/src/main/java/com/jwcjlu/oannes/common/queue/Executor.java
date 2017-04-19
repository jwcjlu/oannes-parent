package com.jwcjlu.oannes.common.queue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Executor {
	private ExecutorService  service=Executors.newFixedThreadPool(3);
	
	public void execute(Action action){
		 service.execute(action);
	}
	public void submit(	Action a){
		service.submit(a);
	}

}
