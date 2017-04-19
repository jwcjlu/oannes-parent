package com.jwcjlu.oannes.config;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;

import com.jwcjlu.oannes.common.Constants;
import com.jwcjlu.oannes.common.NetUtil;
import com.jwcjlu.oannes.common.URL;
import com.jwcjlu.oannes.register.Register;
import com.jwcjlu.oannes.register.ZookeeperRegister;
import com.jwcjlu.oannes.transport.NettyServer;
import com.jwcjlu.oannes.transport.Server;

public class ServiceBean<T> extends ConfigBean implements InitializingBean{
	/**
	 * 
	 */
	private static ExecutorService  service=Executors.newFixedThreadPool(10);
	private static Server server;
	private  static  volatile boolean export=false;
	private static final long serialVersionUID = 1L;
	private static ApplicationContext context;
	public ServiceBean(ApplicationContext context){
		this.context=context;
	}
	public ApplicationContext getContext() {
		return context;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		lock.lock();
		try{
			if(!export){
				new Thread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							export();
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (NoSuchMethodException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SecurityException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}
				).start();
			
				export=true;
			}
			RegisterBean registerBean=context.getBean(RegisterBean.class);
			Register  register= ZookeeperRegister.getInstance(registerBean.getString("register"));
			String path=NetUtil.getRemoteAddress().getHostAddress()+":"+registerBean.getString("port");
			int port=Integer.valueOf(registerBean.getString("port"));
			String persisPath="/"+Constants.ROOT+"/"+getInterfaces().getName()+"/"+Constants.PROVIDER;
			URL url=new URL(NetUtil.getRemoteAddress().getHostAddress(),port, path, persisPath);
			register.register(url);
			
		}finally{
			lock.unlock();
		}
		
	}
	@SuppressWarnings("unchecked")
	private final void export() throws IOException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		RegisterBean registerBean=context.getBean(RegisterBean.class);
		int port=Integer.valueOf(registerBean.getString("port"));
		server=new NettyServer(NetUtil.getRemoteAddress().getHostAddress(), port);
		/*	service.execute(new Runnable() {
			@Override
			public void run() {
				try{
				RegisterBean registerBean=context.getBean(RegisterBean.class);
				int port=Integer.valueOf(registerBean.getString("port"));
				ServerSocket ss = new ServerSocket();
				SocketAddress endpoint=new InetSocketAddress(NetUtil.getRemoteAddress().getHostAddress(), port);
				ss.bind(endpoint);
				while(true){
					 Socket s=	ss.accept();
					 ObjectInputStream ois= new ObjectInputStream( s.getInputStream());
					 RpcRequest req=(RpcRequest) ois.readObject();
					 Object obj=context.getBean(getInterfaces());
					 Method method= obj.getClass().getDeclaredMethod(req.getMethod(), req.getParameterTypes());
					 Object result=method.invoke(obj, req.getArgs());
					 RpcResponse res=new RpcResponse();
					 res.setCode(1000);
					 res.setResult(result);
					 ObjectOutputStream output=new ObjectOutputStream(s.getOutputStream()) ;
					 output.writeObject(res);
					 output.flush();
					 ois.close();
					 output.close();
					 s.close();
				 
				}
				}catch(Exception e){
					System.out.println("服务端出错！！");
				}
				
			}
		});*/
		
		
	}
	

}
