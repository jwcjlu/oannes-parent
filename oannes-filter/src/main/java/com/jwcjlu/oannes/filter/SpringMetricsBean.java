package com.jwcjlu.oannes.filter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

public class SpringMetricsBean {
	 /**
     * 实例化一个registry，最核心的一个模块，相当于一个应用程序的metrics系统的容器，维护一个Map
     */
    private static final MetricRegistry metrics = new MetricRegistry();
    
    private static final Timer timer=metrics.timer("executeTime");
    
    private static final Counter counter=metrics.counter("requestCount");

    /**
     * 在控制台上打印输出
     */
    private static ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics).build();

    public static void metric(){
    	counter.inc();
    	timer.time().stop();
    }
	static {
		reporter.start(5, TimeUnit.SECONDS);
	}
	public static void main(String[] args) throws IOException {
		System.in.read();
	}

}
