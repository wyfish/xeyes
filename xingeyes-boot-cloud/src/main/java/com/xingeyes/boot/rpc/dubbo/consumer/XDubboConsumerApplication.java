package com.xingeyes.boot.rpc.dubbo.consumer;

import org.apache.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Dubbo 集成Spring-Boot 客户端示例
 * 备注： 这里的consumer层只依赖于service 层（通常service层只定义调用的接口）
 */
@SpringBootApplication
@EnableDubboConfig
@RestController
public class XDubboConsumerApplication {

    @Autowired
    private XDubboConsumerService xDubboConsumerService;

    /**
     *
     * @param args
     */
    public static void main(String[] args){
        SpringApplication.run(XDubboConsumerApplication.class, args);
    }

    @RequestMapping(value = "/")
    public String demo(){
       return xDubboConsumerService.demo();
    }

}
