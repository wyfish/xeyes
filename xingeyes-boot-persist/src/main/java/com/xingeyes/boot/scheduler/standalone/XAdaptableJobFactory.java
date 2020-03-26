package com.xingeyes.boot.scheduler.standalone;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;

/**
 * 2.0 Quartz版本以前的Job是Spring自动扫描的，属性可以自动注入，现在换成使用单独的Quartz，属性不能注入了。
 * 一般情况下，quartz的job中使用autowired注解注入的对象为空，这时候我们就要使用spring-quartz提供的AdaptableJobFactory类。
 */
public class XAdaptableJobFactory extends AdaptableJobFactory {

    @Autowired
    private AutowireCapableBeanFactory capableBeanFactory;

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        //调用父类的方法
        Object jobInstance = super.createJobInstance(bundle);
        //进行注入
        capableBeanFactory.autowireBean(jobInstance);
        return jobInstance;
    }

}
