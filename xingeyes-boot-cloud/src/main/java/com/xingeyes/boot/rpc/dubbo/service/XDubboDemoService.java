package com.xingeyes.boot.rpc.dubbo.service;

/**
 * 暴露给Dubbo客户端的服务调用接口
 * 以 jar包的形式供客户端调用
 */
public interface XDubboDemoService {
    /**
     *
     */
    public String demo();
}
