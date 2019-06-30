import com.jwcjlu.oannes.DemoAction;
import com.jwcjlu.oannes.config.annotation.OannesEnable;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

public class ConsumerMain {
	@Configuration
	@OannesEnable(scanBasePackages = "com.jwcjlu.oannes")
	@ComponentScan(value = {"org.apache"})
	@PropertySource("classpath:/spring/oannes-consumer.properties")
	static class ConsumerConfiguration {

	}
	@Test
	public void test() throws Exception {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsumerConfiguration.class);
		context.start();
		DemoAction demoAction= (DemoAction) context.getBean("demoAction");
		demoAction.start();
		System.in.read();
	}
}
