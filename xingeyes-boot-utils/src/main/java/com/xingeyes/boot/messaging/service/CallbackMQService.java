package com.xingeyes.boot.messaging.service;

public interface CallbackMQService {

    /**
     * 发送MQ消息到Callback 消息队列
     * @param request
     */
    public void sendToCallback (CallBackMQRequest request);

}
