package com.jwcjlu.oannes.common.queue;

public abstract class Action implements Runnable{
	private Queue<Action> queue;

	public Action(Queue<Action> queue){
		this.queue=queue;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			this.execute();
		}finally{
			queue.deQueue(this);
		}
		
	}
	public abstract void execute();

}
