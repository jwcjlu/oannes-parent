package com.jwcjlu.oannes.common;

import java.io.Serializable;

public class RpcResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object result;
	private int code;
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
}
