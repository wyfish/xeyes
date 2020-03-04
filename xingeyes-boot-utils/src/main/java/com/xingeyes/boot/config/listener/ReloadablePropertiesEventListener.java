package com.xingeyes.boot.config.listener;


import com.xingeyes.boot.application.XEyesSpringContext;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.ConfigurationBuilderEvent;
import org.apache.commons.configuration2.builder.ReloadingFileBasedConfigurationBuilder;
import org.apache.commons.configuration2.event.EventListener;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.spring.ConfigurationPropertiesFactoryBean;
import org.springframework.beans.factory.config.PropertiesFactoryBean;

public class ReloadablePropertiesEventListener implements EventListener<ConfigurationBuilderEvent> {

    // Spring Framework Properties Factory Bean
    PropertiesFactoryBean propertiesFactoryBean;

    ReloadingFileBasedConfigurationBuilder<PropertiesConfiguration>[] builders;

    // Apache Common Configuration Properties Factory Bean
    ConfigurationPropertiesFactoryBean configurationPropertiesFactoryBean;

    /**
     * 接口实现方法
     * @param configurationBuilderEvent
     */
    @Override
    public void onEvent(ConfigurationBuilderEvent configurationBuilderEvent) {
        //初始化配置数组
        Configuration[] configurations = new Configuration[builders.length];
        for (int i=0; i < builders.length; i++){
            try {
                //配置数组赋值
                configurations[i] = builders[i].getConfiguration();
            }catch (ConfigurationException e)
            {
                // TODO:
            }
        }
        // 如果属性值有变化，首先清除老的属性配置，然后再把变化的属性注入
        this.configurationPropertiesFactoryBean.getConfiguration().clear();
        this.configurationPropertiesFactoryBean.setConfigurations(configurations);

        try{
            this.configurationPropertiesFactoryBean.afterPropertiesSet();
        }catch(Exception e){
            // TODO:
        }

        try{
            //获取Spring框架中的属性工厂Bean
            this.propertiesFactoryBean = (PropertiesFactoryBean) XEyesSpringContext.getApplicationContext().
                    getBean(PropertiesFactoryBean.class);

            // 把Apache Common 包中实现并获取到的Bean赋予Spring的属性工厂Bean
            this.propertiesFactoryBean.setProperties(this.configurationPropertiesFactoryBean.getObject());
            //
            this.propertiesFactoryBean.afterPropertiesSet();
        }catch (Exception e){
            // TODO:
        }
    }

    /**
     * 构造函数
     * @param builders
     * @param configurationPropertiesFactoryBean
     */
    public ReloadablePropertiesEventListener(ReloadingFileBasedConfigurationBuilder<PropertiesConfiguration>[] builders,
                                             ConfigurationPropertiesFactoryBean configurationPropertiesFactoryBean){
        super();
        this.builders = builders;
        this.configurationPropertiesFactoryBean = configurationPropertiesFactoryBean;
    }
}
