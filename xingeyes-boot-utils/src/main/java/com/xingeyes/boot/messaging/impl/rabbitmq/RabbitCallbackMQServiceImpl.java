package com.xingeyes.boot.messaging.impl.rabbitmq;

import com.xingeyes.boot.json.jackson.XJacksonJsonUtil;
import com.xingeyes.boot.messaging.service.CallBackMQRequest;
import com.xingeyes.boot.messaging.service.CallbackMQService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitCallbackMQServiceImpl implements CallbackMQService {

    private static final Logger logger = LoggerFactory.getLogger(RabbitCallbackMQServiceImpl.class);

    @Autowired
    // @Resource(name="cbRabbitTemplate")
    private RabbitTemplate cbRabbitTemplate;

    /**
     * 发送MQ消息到Callback 消息队列
     * @param request
     */
    @Override
    public void sendToCallback (CallBackMQRequest request){
        logger.info("TODO:");

        try {
            cbRabbitTemplate.convertAndSend(XJacksonJsonUtil.format(request));
        }catch(Exception e){
            logger.error("TODO:");
        }
    }
}
