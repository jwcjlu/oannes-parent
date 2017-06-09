package com.jwcjlu.oannes.register;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.CuratorFrameworkFactory.Builder;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException.NoNodeException;
import org.apache.zookeeper.WatchedEvent;
import org.springframework.util.StringUtils;

import com.oannes.common.URL;

public class ZkClientService implements ZkClient {
	private final CuratorFramework client;

	private final Set<StateListener> stateListeners = new CopyOnWriteArraySet<StateListener>();

	private final ConcurrentMap<String, ConcurrentMap<ChildListener, CuratorWatcher>> childListeners = new ConcurrentHashMap<String, ConcurrentMap<ChildListener, CuratorWatcher>>();

	
	public ZkClientService(URL url) throws IOException {
		Builder builder = CuratorFrameworkFactory.builder()
				.connectString(url.getBackupAddress())
		        .retryPolicy(new RetryNTimes(Integer.MAX_VALUE, 1000))  
		        .connectionTimeoutMs(5000);
		String authority = url.getAuthority();
		if (!StringUtils.isEmpty(authority)) {
			builder = builder.authorization("digest", authority.getBytes());
		}
		client = builder.build();
		client.getConnectionStateListenable().addListener(new ConnectionStateListener() {
			@Override
		  public void stateChanged(CuratorFramework client, ConnectionState newState) {
				// TODO Auto-generated method stub
				if (newState == ConnectionState.LOST) {
					ZkClientService.this.stateChanged(StateListener.DISCONNECTED);
				} else if (newState == ConnectionState.CONNECTED) {
					ZkClientService.this.stateChanged(StateListener.CONNECTED);
				} else if (newState == ConnectionState.RECONNECTED) {
					ZkClientService.this.stateChanged(StateListener.RECONNECTED);
				}
			}
		});
		client.start();
	}

	@Override
	public void createNode( String path,String data) throws Exception {
		// TODO Auto-generated method stub
		byte[]d=data.getBytes();
		client.create().creatingParentsIfNeeded().forPath(path,d);
	}

	@Override
	public void createEphrmeralNode( String path,String data) throws Exception {
		// TODO Auto-generated method stub
		byte[]d=data.getBytes();
		client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path,d);
	}

	@Override
	public boolean isExist( String path) throws Exception {
		// TODO Auto-generated method stub
		return client.checkExists().forPath(path)==null;
	}

	@Override
	public void deleteNode( String path) throws Exception {
		// TODO Auto-generated method stub
		client.delete().forPath(path);
	}

	@Override
	public List<String> getChilder( String path) throws Exception {
		// TODO Auto-generated method stub
		return client.getChildren().forPath(path);
	}
	protected void stateChanged(int reconnected) {
		// TODO Auto-generated method stub
		for(StateListener listener:stateListeners){
			listener.stateChanged(reconnected);
		}
		
	}
	public void addListener(StateListener listener){
		stateListeners.add(listener);
	}

	private class CuratorWatcherImpl implements CuratorWatcher {
		
		private volatile ChildListener listener;
		
		public CuratorWatcherImpl(ChildListener listener) {
			this.listener = listener;
		}
		
		public void unwatch() {
			this.listener = null;
		}
		
		public void process(WatchedEvent event) throws Exception {
			if (listener != null) {
				listener.childChanged(event.getPath(), client.getChildren().usingWatcher(this).forPath(event.getPath()));
			}
		}
	}

	public List<String> addChildListener(String path, final ChildListener listener) {
		ConcurrentMap<ChildListener, CuratorWatcher> listeners = childListeners.get(path);
		if (listeners == null) {
			childListeners.putIfAbsent(path, new ConcurrentHashMap<ChildListener, CuratorWatcher>());
			listeners = childListeners.get(path);
		}
		CuratorWatcher targetListener = listeners.get(listener);
		if (targetListener == null) {
			listeners.putIfAbsent(listener, createTargetChildListener(path, listener));
			targetListener = listeners.get(listener);
		}
		return addTargetChildListener(path, targetListener);
	}

	


	public void removeChildListener(String path, ChildListener listener) {
		ConcurrentMap<ChildListener, CuratorWatcher> listeners = childListeners.get(path);
		if (listeners != null) {
			CuratorWatcher targetListener = listeners.remove(listener);
			if (targetListener != null) {
				removeTargetChildListener(path, targetListener);
			}
		}
	}
	private List<String> addTargetChildListener(String path, CuratorWatcher listener) {
		// TODO Auto-generated method stub
		try {
			return client.getChildren().usingWatcher(listener).forPath(path);
		} catch (NoNodeException e) {
			return null;
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
	private void removeTargetChildListener(String path, CuratorWatcher listener) {
		// TODO Auto-generated method stub
		((CuratorWatcherImpl) listener).unwatch();
	}

	private CuratorWatcher createTargetChildListener(String path, ChildListener listener) {
		// TODO Auto-generated method stub
		return new CuratorWatcherImpl(listener);
	}

}
