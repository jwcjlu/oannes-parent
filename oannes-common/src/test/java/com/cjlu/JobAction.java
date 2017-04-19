package com.cjlu;

import java.util.concurrent.CountDownLatch;

import com.jwcjlu.oannes.common.queue.Action;
import com.jwcjlu.oannes.common.queue.DefaultQueue;
import com.jwcjlu.oannes.common.queue.Executor;
import com.jwcjlu.oannes.common.queue.Queue;

public class JobAction extends Action{

	private int taskNumber;
	private CountDownLatch  latch;
	public JobAction(Queue<Action> queue,int number,CountDownLatch  latch) {
		super(queue);
		this.taskNumber=number;
		this.latch=latch;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
			System.out.println(String.format("%s第%s个任务执行完了！",Thread.currentThread().getName(), taskNumber));
			latch.countDown();
	
		
	}
	public static void main(String[] args) throws InterruptedException {
		Executor  exe=new Executor();
		Queue<Action> q=new DefaultQueue(exe);
		long startTime=System.currentTimeMillis();
		CountDownLatch  latch=new CountDownLatch(100000);
		for(int i=0;i<100000;i++){
			Action a=new JobAction(q,i,latch);
			q.enQueue(a);
		}
		latch.await();
		long end=System.currentTimeMillis()-startTime;
		System.out.println(end);
	}
}
