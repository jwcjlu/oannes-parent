package com.jwcjlu.oannes;

import java.util.concurrent.CompletableFuture;

public interface DemoService {
	String sayHello(String msg);
	CompletableFuture<String> sayHi(String msg);

}
