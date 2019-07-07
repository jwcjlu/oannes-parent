package com.oannes.common;

import java.lang.reflect.Method;

public interface Invocation {
	Method  getMethod();
	
	Object[] getAgrs();
	
	void addAttribute(String key,Object value);
	
	void removeAttribute(String key);
	
	Class<?> getInterface();
	Result newResult();
	boolean isAsyn();

}
