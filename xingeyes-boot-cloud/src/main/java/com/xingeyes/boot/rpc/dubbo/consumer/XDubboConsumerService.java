package com.xingeyes.boot.rpc.dubbo.consumer;

import com.xingeyes.boot.rpc.dubbo.service.XDubboDemoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

@Component
public class XDubboConsumerService {

    @Reference
    private XDubboDemoService xDubboDemoService;

    /**
     *
     */
    public String demo (){
        return xDubboDemoService.demo();
    }
}
