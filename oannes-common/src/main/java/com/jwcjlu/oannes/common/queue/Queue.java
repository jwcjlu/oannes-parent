package com.jwcjlu.oannes.common.queue;

public interface Queue<E> {
	/**
	 * 进栈
	 * @param e
	 */
	public void enQueue(E e);
	/**
	 * 出栈
	 * @param e
	 */
	public void deQueue(E e);
	/**
	 * 队列大小
	 * @return
	 */
	
	public int size();

}
