package com.jwcjlu.oannes.transport;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import io.netty.util.NetUtil;

public abstract class AbstractClient implements Client {
	private SocketAddress connectAddress;
	private Lock connectLock = new ReentrantLock();

	public AbstractClient(String host, int port) {
		connectAddress = new InetSocketAddress(host, port);
		try {
			doOpen();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			try {
				close();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public void connect() throws  RemoteException {
		connectLock.lock();
		try {
			if (isConnected()) {
				return;
			}
			doConnect();
			if (!isConnected()) {
				throw new RemoteException("Failed connect to server " + getConnectAddress() + " from "
						+ getClass().getSimpleName() + " " + NetUtil.LOCALHOST.getHostAddress()
						+ " using dubbo version " + ", cause: Connect wait timeout: " + "ms.");
			}
		} catch (RemoteException e) {
			throw e;
		} finally {
			connectLock.unlock();
		}
	}

	@Override
	public void reconnect() {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() throws RemoteException {
		// TODO Auto-generated method stub
		connectLock.lock();
		try {
			try {
				doClose();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} finally {
			connectLock.unlock();
		}

	}

	protected abstract void doClose() throws InterruptedException;
	protected  abstract void  doReconnect();



	public SocketAddress getConnectAddress() {
		return connectAddress;
	}

	public void setConnectAddress(SocketAddress connectAddress) {
		this.connectAddress = connectAddress;
	}

	protected abstract void doConnect();

	public abstract void doOpen() throws RemoteException;

}
