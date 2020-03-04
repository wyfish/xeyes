package com.xingeyes.boot.messaging.impl.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class RabbitMQConsumer {

    private static Logger logger = LoggerFactory.getLogger(RabbitMQConsumer.class);

    @RabbitListener(queues = "cord")
    public void processMessage (String msg) {
        logger.info("Log Something here!");
        // TODO;
    }
}
