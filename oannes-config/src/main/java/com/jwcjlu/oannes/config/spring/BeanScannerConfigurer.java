package com.jwcjlu.oannes.config.spring;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;

import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import com.jwcjlu.oannes.config.ConsumerBean;
import com.jwcjlu.oannes.config.OannConsumer;
import com.jwcjlu.oannes.config.OannService;
import com.jwcjlu.oannes.config.RegisterBean;
import com.jwcjlu.oannes.config.ServiceBean;


public class BeanScannerConfigurer  implements DisposableBean, BeanFactoryPostProcessor,BeanPostProcessor, ApplicationContextAware {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(BeanScannerConfigurer.class);
	private ApplicationContext applicationContext;
    private String id;
    private  ManagedMap parameters;
    private String basePackage;

    private final Set<ServiceBean<?>> serviceConfigs = new ConcurrentSkipListSet<ServiceBean<?>>();

    private final ConcurrentMap<String, ConsumerBean<?>> referenceConfigs = new ConcurrentHashMap<String, ConsumerBean<?>>();

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		Object obj=parameters.get("basePackage");
		if (obj == null || obj.toString().length() == 0) {
	            return;
	        }
	        if (beanFactory instanceof BeanDefinitionRegistry) {
	            try {
	             basePackage=obj.toString();
	                // init scanner
	                ClassPathBeanDefinitionScanner scanner =new ClassPathBeanDefinitionScanner((BeanDefinitionRegistry) beanFactory);
	                // add filter
	                AnnotationTypeFilter  includeFilter=new AnnotationTypeFilter(OannService.class);
	                scanner.addIncludeFilter(includeFilter);
	                scanner.scan(basePackage);
	            } catch (Throwable e) {
	                // spring 2.0
	            }
	        }
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		// TODO Auto-generated method stub
		 if (! isMatchPackage(bean)) {
	            return bean;
	        }
		 OannService oService= bean.getClass().getAnnotation(OannService.class);
		 if(oService!=null){
			 Class type=oService.interfaces();
			 ServiceBean<Object> serv=new ServiceBean<Object>(applicationContext);
			 serv.setInterfaces(type);
			 serv.setHost(oService.host());
			 serv.setPort(oService.port());
			 serv.setRegisterBean(applicationContext.getBean(RegisterBean.class));
			 try {
				serv.afterPropertiesSet();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		// TODO Auto-generated method stub
		 if (! isMatchPackage(bean)) {
	            return bean;
	        }
	        Method[] methods = bean.getClass().getMethods();
	        for (Method method : methods) {
	            String name = method.getName();
	            if (name.length() > 3 && name.startsWith("set")
	                    && method.getParameterTypes().length == 1
	                    && Modifier.isPublic(method.getModifiers())
	                    && ! Modifier.isStatic(method.getModifiers())) {
	                try {
	                	OannConsumer reference = method.getAnnotation(OannConsumer.class);
	                	if (reference != null) {
		                	Object value = refer(reference, method.getParameterTypes()[0]);
		                	if (value != null) {
		                		method.invoke(bean, new Object[] {  });
		                	}
	                	}
	                } catch (Throwable e) {
	                }
	            }
	        }
	        Field[] fields = bean.getClass().getDeclaredFields();
	        for (Field field : fields) {
	            try {
	                if (! field.isAccessible()) {
	                    field.setAccessible(true);
	                }
	                OannConsumer reference = field.getAnnotation(OannConsumer.class);
	            	if (reference != null) {
		                Object value = refer(reference, field.getType());
		                if (value != null) {
		                	field.set(bean, value);
		                }
	            	}
	            } catch (Throwable e) {
	            	System.out.println("Failed to init remote service reference at filed " + field.getName() + " in class " + bean.getClass().getName() + ", cause: " + e.getMessage());
	            }
	        }
	        return bean;
	}
	 

	private boolean isMatchPackage(Object bean) {
	        if (basePackage == null || basePackage.length() == 0) {
	            return true;
	        }
	        String beanClassName = bean.getClass().getName();
	    
	            if (beanClassName.startsWith(basePackage)) {
	                return true;
	            
	        }
	        return false;
	    }
	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ManagedMap getParameters() {
		return parameters;
	}

	public void setParameters(ManagedMap parameters) {
		this.parameters = parameters;
	}
    private Object refer(OannConsumer reference, Class<?> type) throws Exception {
			// TODO Auto-generated method stub
    	ConsumerBean<Object> consumer=new ConsumerBean<Object>();
    	consumer.setHost(reference.host());
    	consumer.setPort(reference.port());
    	consumer.setInterfaces(reference.interfaces());
    	consumer.setRegisterBean(applicationContext.getBean(RegisterBean.class));
    	consumer.afterPropertiesSet();
		return consumer.getObject();
	}
	
}