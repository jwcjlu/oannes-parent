package com.jwcjlu.oannes.register;

import java.util.List;

public interface ZkClient {
	/**
	 * 
	 * @param client
	 * @param path
	 * @throws Exception 
	 */
	void createNode(String path,String data) throws Exception;
	/**
	 * 
	 * @param client
	 * @param path
	 * @throws Exception 
	 */
	void createEphrmeralNode(String path,String data) throws Exception;
	/**
	 * 
	 * @param client
	 * @param path
	 * @return
	 * @throws Exception 
	 */
	boolean isExist(String path) throws Exception;
	/**
	 * 
	 * @param client
	 * @param path
	 * @throws Exception 
	 */
	void deleteNode(String path) throws Exception;
	/**
	 * 
	 * @param client
	 * @param path
	 * @return
	 * @throws Exception 
	 */
	List<String> getChilder(String path) throws Exception;
	/**
	 * 
	 * @param listener
	 */
	public void addListener(StateListener listener);
	/**
	 * 
	 * @param path
	 * @param zkListener
	 * @return
	 * @throws Exception 
	 */
	List<String> addChildListener(String path, ChildListener zkListener) throws Exception;
	/**
	 * 
	 * @param urlPath
	 * @param zkListener
	 */
	void removeChildListener(String urlPath, ChildListener zkListener);

}
