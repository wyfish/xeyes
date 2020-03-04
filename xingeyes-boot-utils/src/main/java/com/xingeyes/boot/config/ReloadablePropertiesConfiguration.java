package com.xingeyes.boot.config;

import com.xingeyes.boot.config.listener.ReloadablePropertiesEventListener;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.ConfigurationBuilderEvent;
import org.apache.commons.configuration2.builder.ReloadingFileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.reloading.PeriodicReloadingTrigger;
import org.apache.commons.configuration2.spring.ConfigurationPropertiesFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.naming.ConfigurationException;
import java.io.File;
import java.util.concurrent.TimeUnit;

@Configuration
public class ReloadablePropertiesConfiguration {

    private static final String PROPERTIES_FILE_EXTENSION = ".properties";

    private ReloadingFileBasedConfigurationBuilder<PropertiesConfiguration>[] builders;

    @Autowired
    private ApplicationPath applicationPath;

    /**
     * 把Apache Comm的属性工厂Bean注入到Spring 的属性工厂Bean
     * @return
     * @throws ConfigurationException
     */
    @Bean("configurationPropertiesFactoryBean")
    public ConfigurationPropertiesFactoryBean getConfigurationPropertiesFactoryBean() throws ConfigurationException {
        // 实例化Apache Comm工具包中的属性工厂Bean
        ConfigurationPropertiesFactoryBean commFactoryBean = new ConfigurationPropertiesFactoryBean();

        //获取所有属性文件的属性并注入属性工厂Bean (Apache Common - ConfigurationPropertiesFactoryBean)
        commFactoryBean.setConfigurations(getReloadableConfiguration());

        //对于属性文件增加事件监听器
        for (ReloadingFileBasedConfigurationBuilder<PropertiesConfiguration> builder: builders ) {
            builder.addEventListener(ConfigurationBuilderEvent.RESET,
                    new ReloadablePropertiesEventListener(builders, commFactoryBean));
        }
        return commFactoryBean;
    }

    /**
     * 私有方法：获取应用目录下所有属性文件的属性配置集合
     * @return
     * @throws ConfigurationException
     */
    private PropertiesConfiguration[] getReloadableConfiguration() throws ConfigurationException {
        // 获取属性文件的根目录
        File path = new File(applicationPath.getConfPath());
        // 遍历所有属性文件
        File[] propertiesFiles = path.listFiles((File pathName) -> pathName.getName().endsWith(PROPERTIES_FILE_EXTENSION));

        // 根据所有属性文件的数值 来实例化属性配置数组
        PropertiesConfiguration[] configs = new PropertiesConfiguration[propertiesFiles.length];
        // 根据所有属性文件的数值来实例化Builer数组
        builders = new ReloadingFileBasedConfigurationBuilder[propertiesFiles.length];

        for (int i = 0; i < propertiesFiles.length; i++) {

            Parameters parameters = new Parameters();

            builders[i] = new ReloadingFileBasedConfigurationBuilder<PropertiesConfiguration>(
                    PropertiesConfiguration.class).configure(parameters.fileBased().setFile(propertiesFiles[i]));

            PeriodicReloadingTrigger trigger = new PeriodicReloadingTrigger(builders[i].getReloadingController(),
                    null, 30, TimeUnit.SECONDS);
            trigger.start();

            try {
                configs[i] = builders[i].getConfiguration();
            }catch (Exception e){
                // TODO:
            }
        }
        return configs;
    }
}
