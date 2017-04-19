package com.jwcjlu.oannes.transport;

import io.netty.channel.Channel;

public interface ChannelHandler {
	/**
	 * 连接channel
	 * @param channel
	 * @throws RemoteException
	 */
	void connect(Channel channel)throws RemoteException;
	/**
	 * 发送信息
	 * @param channel
	 * @param msg
	 * @throws RemoteException
	 */
	void send(Channel channel,Object msg)throws RemoteException;
	/**
	 * 发送信息有超时时间
	 * @param channel
	 * @param msg
	 * @param timeOut
	 * @throws RemoteException
	 */
	
	void send(Channel channel,Object msg ,long timeOut)throws RemoteException;
	/**
	 * 接收信息
	 * @param channel
	 * @return
	 * @throws RemoteException
	 */
	
	Object receive(Channel channel)throws RemoteException;
	/**
	 * 异常
	 * @param channel
	 * @param exception
	 * @throws RemoteException
	 */
	
	void caught(Channel channel, Throwable exception) throws RemoteException;

}
