package com.jwcjlu.oannes.config.spring.beanFactory;

import com.jwcjlu.oannes.common.services.BootServiceManager;
import com.jwcjlu.oannes.config.ConsumerBean;
import com.jwcjlu.oannes.config.OannConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class ConsumerAnnotationBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter implements MergedBeanDefinitionPostProcessor
        , PriorityOrdered, BeanFactoryAware, EnvironmentAware {
    private Logger logger = LoggerFactory.getLogger(ConsumerAnnotationBeanPostProcessor.class);
    private final Set<Class<? extends Annotation>> autowiredAnnotationTypes = new LinkedHashSet();
    private int order;
    private Environment environment;
    private Map<String, InjectionMetadata> injectionMetadataCache = new HashMap();
    private Map<String,Object>cacheBeans=new HashMap<>();
    private String requiredParameterName = "required";
    private boolean requiredParameterValue = true;
    private ConfigurableListableBeanFactory beanFactory;
    public static final String BEAN_NAME = "consumerAnnotationBeanPostProcessor";

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public ConsumerAnnotationBeanPostProcessor() {
        autowiredAnnotationTypes.add(OannConsumer.class);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {
        if (beanType != null) {
            InjectionMetadata metadata = this.findAutowiringMetadata(beanName, beanType, (PropertyValues) null);
            metadata.checkConfigMembers(beanDefinition);
        }
    }

    public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds,
                                                    Object bean, String beanName) throws BeansException {

        InjectionMetadata metadata = this.findAutowiringMetadata(beanName, bean.getClass(), pvs);
        try {
            metadata.inject(bean, beanName, pvs);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return pvs;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return this.order;
    }


    private InjectionMetadata findAutowiringMetadata(String beanName, Class<?> clazz, PropertyValues pvs) {
        String cacheKey = StringUtils.hasLength(beanName) ? beanName : clazz.getName();
        InjectionMetadata metadata = (InjectionMetadata) this.injectionMetadataCache.get(cacheKey);
        if (InjectionMetadata.needsRefresh(metadata, clazz)) {
            Map var6 = this.injectionMetadataCache;
            synchronized (this.injectionMetadataCache) {
                metadata = (InjectionMetadata) this.injectionMetadataCache.get(cacheKey);
                if (InjectionMetadata.needsRefresh(metadata, clazz)) {
                    if (metadata != null) {
                        metadata.clear(pvs);
                    }

                    try {
                        metadata = this.buildConsumerMetadata(clazz);
                        this.injectionMetadataCache.put(cacheKey, metadata);
                    } catch (NoClassDefFoundError var9) {
                        throw new IllegalStateException("Failed to introspect bean class [" + clazz.getName() + "] for autowiring metadata: could not find class that it depends on", var9);
                    }
                }
            }
        }

        return metadata;
    }

    private InjectionMetadata buildConsumerMetadata(Class<?> clazz) {
        LinkedList<InjectionMetadata.InjectedElement> elements = new LinkedList();
        Class targetClass = clazz;

        do {
            final LinkedList<InjectionMetadata.InjectedElement> currElements = new LinkedList();
            ReflectionUtils.doWithLocalFields(targetClass, new ReflectionUtils.FieldCallback() {
                public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                    AnnotationAttributes ann = ConsumerAnnotationBeanPostProcessor.this.findAutowiredAnnotation(field);
                    if (ann != null) {
                        if (Modifier.isStatic(field.getModifiers())) {
                            if (ConsumerAnnotationBeanPostProcessor.this.logger.isWarnEnabled()) {
                                ConsumerAnnotationBeanPostProcessor.this.logger.warn("Autowired annotation is not supported on static fields: " + field);
                            }

                            return;
                        }

                        boolean required = ConsumerAnnotationBeanPostProcessor.this.determineRequiredStatus(ann);
                        currElements.add(ConsumerAnnotationBeanPostProcessor.this.new AutowiredFieldElement(field, required));
                    }

                }
            });
            elements.addAll(0, currElements);
            targetClass = targetClass.getSuperclass();
        } while (targetClass != null && targetClass != Object.class);

        return new InjectionMetadata(clazz, elements);
    }

    private AnnotationAttributes findAutowiredAnnotation(AccessibleObject ao) {
        if (ao.getAnnotations().length > 0) {
            Iterator var2 = this.autowiredAnnotationTypes.iterator();

            while (var2.hasNext()) {
                Class<? extends Annotation> type = (Class) var2.next();
                AnnotationAttributes attributes = AnnotatedElementUtils.getMergedAnnotationAttributes(ao, type);
                if (attributes != null) {
                    return attributes;
                }
            }
        }

        return null;
    }

    protected boolean determineRequiredStatus(AnnotationAttributes ann) {
        return !ann.containsKey(this.requiredParameterName) || this.requiredParameterValue == ann.getBoolean(this.requiredParameterName);
    }


    private class AutowiredFieldElement extends InjectionMetadata.InjectedElement {
        private final boolean required;
        private volatile boolean cached = false;
        private volatile Object cachedFieldValue;

        public AutowiredFieldElement(Field field, boolean required) {
            super(field, (PropertyDescriptor) null);
            this.required = required;
        }

        protected void inject(Object bean, String beanName, PropertyValues pvs) throws Throwable {
            Field field = (Field) this.member;
            OannConsumer consumer=field.getAnnotation(OannConsumer.class);
            Object value;
            if (this.cached) {
                value = ConsumerAnnotationBeanPostProcessor.this.resolvedCachedArgument(beanName, this.cachedFieldValue);
            } else {
               ConsumerBean consumerBean=new ConsumerBean();
                consumerBean.setter(consumer);
                consumerBean.setEnvironment(environment);
                value = consumerBean.getObject();
                cacheBeans.put(beanName,value);
                cached=true;

            }
            if (value != null) {
                ReflectionUtils.makeAccessible(field);
                field.set(bean, value);
            }

        }
    }

    private Object resolvedCachedArgument(String beanName, Object cachedFieldValue) {
        return cacheBeans.get(beanName);
    }
}
