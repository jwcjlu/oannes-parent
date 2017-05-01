import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jwcjlu.oannes.DemoAction;

public class AppMain {
	public static void main(String[] args) throws Exception {
		ApplicationContext  ac=new ClassPathXmlApplicationContext("spring.xml");
		
		DemoAction action=ac.getBean(DemoAction.class);
		action.start();
		
	   
	}
}
