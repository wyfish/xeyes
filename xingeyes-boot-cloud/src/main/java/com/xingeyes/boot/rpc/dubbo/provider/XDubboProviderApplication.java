package com.xingeyes.boot.rpc.dubbo.provider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Dubbo 集成Spring-Boot 服务提供端示例
 * 备注： 这里的provider层只依赖于serviceimpl 层和dao层
 */
@SpringBootApplication
@EnableDubboConfig
public class XDubboProviderApplication {

    /**
     *
     * @param args
     */
    public static void main(String[] args){
        SpringApplication.run(XDubboProviderApplication.class, args);
    }
}
