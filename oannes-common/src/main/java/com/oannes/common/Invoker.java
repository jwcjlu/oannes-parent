package com.oannes.common;

public interface Invoker<T> {
	
	URL getURL();
	
	Object invoke(Invocation invocation);

}
