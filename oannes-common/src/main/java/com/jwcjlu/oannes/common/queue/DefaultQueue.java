package com.jwcjlu.oannes.common.queue;

import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

public class DefaultQueue implements Queue<Action>{
	private ReentrantLock lock=new ReentrantLock();
	private LinkedList<Action> queue=new LinkedList<Action>();
	private Executor executor;
	public DefaultQueue(Executor exe){
		this.executor=exe;
	}
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return queue.size();
	}

	@Override
	public void enQueue(Action e) {
		// TODO Auto-generated method stub
		int size=0;
		try{
			lock.lock();
			queue.add(e);
			size=queue.size();
		}finally{
			lock.unlock();
		}
		if(size==1){
			executor.execute(e);
		}
		
	}

	@Override
	public void deQueue(Action e) {
		// TODO Auto-generated method stub
		Action nextAction=null;
		try{
		
			if(e!=null){
				queue.remove(e);
			}
			nextAction=queue.poll();
		}finally{
		
			if(nextAction!=null){
				executor.execute(nextAction);
			}
		}
	}

}
