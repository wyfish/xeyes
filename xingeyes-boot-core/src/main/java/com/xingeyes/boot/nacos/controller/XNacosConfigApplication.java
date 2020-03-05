package com.xingeyes.boot.nacos.controller;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@NacosPropertySource(dataId = "xeyes", autoRefreshed = true)
public class XNacosConfigApplication {
    public static void main(String[] args) {
        SpringApplication.run(XNacosConfigApplication.class, args);
    }
}
