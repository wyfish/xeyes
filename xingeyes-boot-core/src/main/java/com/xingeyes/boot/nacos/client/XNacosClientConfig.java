package com.xingeyes.boot.nacos.client;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class XNacosClientConfig {

    /**
     * 注入Nacos Configservice 实例，
     * @return 返回注入的实例，如果返回空则表示注入失败
     */
    @Bean("nacosConfigSerivce")
    public ConfigService configService(){
        String serverAddr = "{serverAddr}";
        String dataId = "{dataId}";
        String group = "{group}";
        Properties properties = new Properties();
        properties.setProperty("serverAddr", serverAddr);

        try {
            ConfigService nacosConfigSerivce = NacosFactory.createConfigService(properties);
            String content = nacosConfigSerivce.getConfig(dataId, group, 5000);
            // TODO: 可以定制化配置服务
            return nacosConfigSerivce;
        }catch (NacosException e) {
            return null;
        }
    }

    /**
     * 注入Nacos Namingservice 实例，
     * @return 返回注入的实例，如果返回空则表示注入失败
     */
    @Bean("nacosNamingService")
    public NamingService namingService(){
        try {
            NamingService nacosNamingService = NamingFactory.createNamingService(System.getProperty("serveAddr"));
            return nacosNamingService;
        }catch (NacosException e) {
            return null;
        }
    }
}
