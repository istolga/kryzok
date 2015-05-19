package com.kruzok.api.impl.version;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import com.kruzok.api.exposed.adapter.Adapter;
import com.kruzok.api.exposed.adapter.InputAdapter;
import com.kruzok.api.exposed.adapter.OutputAdapter;


@Component
public class AdapterRegistry implements BeanPostProcessor {
    
    public static final String IDENTIFIER = "V";
    
    
    protected static Log log = LogFactory.getLog(AdapterRegistry.class);
    
    @Value("${api.latest.version}")
    private int latestVersion;
    
    private final Set<Class<?>> supportedOutClasses = new HashSet<Class<?>>();
    private final Set<Class<?>> supportedInClasses = new HashSet<Class<?>>();
    
    
    private final Map<String, Adapter<?, ?>> outAdapters = new HashMap<String, Adapter<?, ?>>();
    private final Map<String, Adapter<?, ?>> inAdapters = new HashMap<String, Adapter<?, ?>>();
    
    
    public void register(Adapter<?, ?> adapter) {
        
        for (int i = 0; i <= latestVersion; i++) {
            if (adapter instanceof InputAdapter) {
                if (adapter.canSupport(i)) {
                    inAdapters.put(generateKey(adapter.getToClass(), i), adapter);
                    supportedInClasses.add(adapter.getToClass());
                }
            }
            if (adapter instanceof OutputAdapter) {
                if (adapter.canSupport(i)) {
                    outAdapters.put(generateKey(adapter.getFromClass(), i), adapter);
                    supportedOutClasses.add(adapter.getFromClass());
                }
            }
        }
    }
    
    Adapter<?, ?> getOutAdapter(String className) {
        return outAdapters.get(className);
    }
    
    Adapter<?, ?> getInAdapter(String className) {
        return inAdapters.get(className);
    }
    
    Set<Class<?>> getSupportedInClasses() {
        return supportedInClasses;
    }
    
    Set<Class<?>> getSupportedOutClasses() {
        return supportedOutClasses;
    }
    
    String generateKey(Class<?> clazz, int version) {
        
        return clazz.getSimpleName() + IDENTIFIER + version;
        
    }
    
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        // register only adapters
        if (log.isDebugEnabled()) {
            log.debug("Post initialization called for " + beanName);
        }
        if (bean instanceof InputAdapter) {
            log.warn("Registering input adapter " + beanName);
            register((InputAdapter<?, ?>) bean);
        }
        else if (bean instanceof OutputAdapter) {
            log.warn("Registering output adapter " + beanName);
            register((OutputAdapter<?, ?>) bean);
        }
        
        return bean;
    }
    
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        // do nothing
        return bean;
    }
    
    // Test only method
    void setLatestVersionForTest(int version) {
        latestVersion = version;
        
    }
}
