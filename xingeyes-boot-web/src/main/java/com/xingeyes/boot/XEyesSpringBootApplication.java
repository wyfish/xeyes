package com.xingeyes.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class XEyesSpringBootApplication {
    /**
     * XEyes Boot 主程序入口
     * @param args
     */
    public static void main(String[] args){
        SpringApplication.run(XEyesSpringBootApplication.class);
    }
}
