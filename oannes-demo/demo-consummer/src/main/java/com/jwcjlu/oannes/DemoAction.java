package com.jwcjlu.oannes;
import com.jwcjlu.oannes.config.OannConsumer;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class DemoAction {
	@OannConsumer(interfaces=DemoService.class,group="jwcjlu",version="2.0")
	private DemoService demoService;
	@OannConsumer(interfaces=HelloService.class,group="jwcjlu",version="1.0")
	private HelloService  service;
	public void start() throws Exception {
		for(int i=0;i<Integer.MAX_VALUE;i++) {
			if(i%2==0){
				String msg=demoService.sayHello("jinwei");
				System.out.println(msg);
				continue;
			}
			CompletableFuture<String> result = demoService.sayHi("world");
			result.whenComplete((v, t) -> {
				if (t != null) {
					t.printStackTrace();
				} else {
					System.out.println(v);
				}
			});
			TimeUnit.MILLISECONDS.sleep(500);

		}

	}
	public DemoService getDemoService() {
		return demoService;
	}
	public void setDemoService(DemoService demoService) {
		this.demoService = demoService;
	}
	public HelloService getService() {
		return service;
	}
	public void setService(HelloService service) {
		this.service = service;
	}
	
}
