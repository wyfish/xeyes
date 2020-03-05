package com.xingeyes.boot.rpc.dubbo.serviceimpl;

import com.xingeyes.boot.rpc.dubbo.service.XDubboDemoService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

@Service(interfaceClass = XDubboDemoService.class)
@Component
public class XDubboDemoServiceImpl implements XDubboDemoService {
    /**
     * demo method
     */
    @Override
    public String demo(){
        // TODO:
        return "Hello world!";
    }
}
