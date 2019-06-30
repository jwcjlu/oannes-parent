package com.jwcjlu.oannes.config.spring.beanFactory;

import com.jwcjlu.oannes.common.spring.SpringBeanUtils;
import com.jwcjlu.oannes.config.OannService;
import com.jwcjlu.oannes.config.ServiceBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.beans.Introspector;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.springframework.beans.factory.support.BeanDefinitionBuilder.rootBeanDefinition;

public class ServiceAnnotationBeanPostProcessor implements BeanDefinitionRegistryPostProcessor, EnvironmentAware,
        ResourceLoaderAware, BeanClassLoaderAware, ApplicationContextAware {
    private Environment environment;
    private ResourceLoader resourceLoader;
    private ClassLoader classLoader;
    private Set<String> basePackages;
    public ServiceAnnotationBeanPostProcessor(Set<String> basePackages){
        this.basePackages=basePackages;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader=classLoader;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        // init scanner
        Set<String> scanBasePackages=resolvePackagesToScan(basePackages);
        ClassPathBeanDefinitionScanner scanner =new ClassPathBeanDefinitionScanner(beanDefinitionRegistry);
        // add filter
        AnnotationTypeFilter includeFilter=new AnnotationTypeFilter(OannService.class);
        scanner.addIncludeFilter(includeFilter);
        BeanNameGenerator generator=new AnnotationBeanNameGenerator();
        for(String packageToScan:scanBasePackages){
            scanner.scan(packageToScan);
            Set<BeanDefinitionHolder> beanDefinitionHolders =
                    findServiceBeanDefinitionHolders(scanner, packageToScan, beanDefinitionRegistry,generator);
            registerServiceBean(beanDefinitionHolders,beanDefinitionRegistry);
        }

    }
    private String buildDefaultBeanName(Class clazz) {
        String shortClassName = ClassUtils.getShortName(clazz);
        return Introspector.decapitalize(shortClassName);
    }
    private void registerServiceBean(Set<BeanDefinitionHolder> beanDefinitionHolders,BeanDefinitionRegistry beanDefinitionRegistry) {
        for(BeanDefinitionHolder holder:beanDefinitionHolders){
            BeanDefinitionBuilder builder = rootBeanDefinition(ServiceBean.class);
            String className=holder.getBeanDefinition().getBeanClassName();
            Class clazz = resolveClass(className, classLoader);
            OannService service= AnnotationUtils.findAnnotation(clazz,OannService.class);
            if(service==null){
              continue;
            }
            Class interfaceClass=service.interfaces();
            builder.addPropertyValue("interfaces",interfaceClass);
            builder.addPropertyValue("oannService",service);
            builder.setLazyInit(false);
            beanDefinitionRegistry.registerBeanDefinition(buildDefaultBeanName(interfaceClass),builder.getBeanDefinition());
        }
    }

    private Set<BeanDefinitionHolder> findServiceBeanDefinitionHolders(
            ClassPathBeanDefinitionScanner scanner, String packageToScan, BeanDefinitionRegistry beanDefinitionRegistry
            ,BeanNameGenerator beanNameGenerator) {
            Set<BeanDefinition> definitions= scanner.findCandidateComponents(packageToScan);
            Set<BeanDefinitionHolder> holders=new HashSet<>(definitions.size());
            for(BeanDefinition beanDefinition:definitions){
               String beanName= beanNameGenerator.generateBeanName(beanDefinition,beanDefinitionRegistry);
               holders.add(new BeanDefinitionHolder(beanDefinition,beanName));
                
            }
            return holders;
    }


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }

    @Override
    public void setEnvironment(Environment environment) {
      this.environment=environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader=resourceLoader;
    }
    private Set<String> resolvePackagesToScan(Set<String> packagesToScan) {
        Set<String> resolvedPackagesToScan = new LinkedHashSet<String>(packagesToScan.size());
        for (String packageToScan : packagesToScan) {
            if (StringUtils.hasText(packageToScan)) {
                String resolvedPackageToScan = environment.resolvePlaceholders(packageToScan.trim());
                resolvedPackagesToScan.add(resolvedPackageToScan);
            }
        }
        return resolvedPackagesToScan;
    }
    public Class resolveClass(String className, ClassLoader classLoader){
        try {
            return Class.forName(className, false,this.classLoader);
        } catch (ClassNotFoundException var3) {
            throw new IllegalArgumentException("Cannot find class [" + className + "]", var3);
        } catch (LinkageError var4) {
            throw new IllegalArgumentException("Error loading class [" + className + "]: problem with class file or dependent class.", var4);
        }
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanUtils.setContext(applicationContext);
    }
}
