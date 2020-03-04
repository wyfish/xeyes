package com.xingeyes.boot.application;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * 实现Spring Application Context 类的加载
 */
@Service
@Lazy(false)
public class XEyesSpringContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        XEyesSpringContext.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() { return applicationContext;}
}
