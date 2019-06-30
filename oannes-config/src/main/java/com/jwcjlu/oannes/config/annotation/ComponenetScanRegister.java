package com.jwcjlu.oannes.config.annotation;

import com.jwcjlu.oannes.common.services.BootServiceManager;
import com.jwcjlu.oannes.config.spring.ServiceManagerStartup;
import com.jwcjlu.oannes.config.spring.beanFactory.ConsumerAnnotationBeanPostProcessor;
import com.jwcjlu.oannes.config.spring.beanFactory.ServiceAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.*;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.springframework.beans.factory.support.BeanDefinitionBuilder.rootBeanDefinition;

public class ComponenetScanRegister implements ImportBeanDefinitionRegistrar  {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        BootServiceManager.INSTANCE.boot();
        Set<String> basePackages=getPackagesToScan(annotationMetadata);
        registerServiceAnnotationBeanPostProcessor(basePackages,beanDefinitionRegistry);
        registerConsumerAnnotationBeanPostProcessor(beanDefinitionRegistry);
        registerStartupBeanPostProcessor(beanDefinitionRegistry);
    }

    private void registerStartupBeanPostProcessor(BeanDefinitionRegistry beanDefinitionRegistry) {
        BeanDefinitionBuilder builder = rootBeanDefinition(ServiceManagerStartup.class);
        builder.setRole(BeanDefinition.ROLE_APPLICATION);
        BeanDefinitionReaderUtils.registerWithGeneratedName(builder.getBeanDefinition(), beanDefinitionRegistry);

    }

    private void registerServiceAnnotationBeanPostProcessor(Set<String> packagesToScan, BeanDefinitionRegistry registry) {

        BeanDefinitionBuilder builder = rootBeanDefinition(ServiceAnnotationBeanPostProcessor.class);
        builder.addConstructorArgValue(packagesToScan);
        builder.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
        BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinition, registry);

    }
    private void registerConsumerAnnotationBeanPostProcessor( BeanDefinitionRegistry registry) {

        RootBeanDefinition beanDefinition = new RootBeanDefinition(ConsumerAnnotationBeanPostProcessor.class);
        beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        registry.registerBeanDefinition(ConsumerAnnotationBeanPostProcessor.BEAN_NAME, beanDefinition);

    }
    private Set<String> getPackagesToScan(AnnotationMetadata metadata) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(
                metadata.getAnnotationAttributes(OannesComponentScan.class.getName()));
        String[] basePackages = attributes.getStringArray("basePackages");
        String[] value = attributes.getStringArray("value");
        // Appends value array attributes
        Set<String> packagesToScan = new LinkedHashSet<String>(Arrays.asList(value));
        packagesToScan.addAll(Arrays.asList(basePackages));
        if (packagesToScan.isEmpty()) {
            return Collections.singleton(ClassUtils.getPackageName(metadata.getClassName()));
        }
        return packagesToScan;
    }


}
