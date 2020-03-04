package com.xingeyes.boot.messaging.impl.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 发往消息网关的回调消息队列实现的配置（RabbitMQ）
 */
@Configuration
public class RabbitMQConfig {

    private static final String topicExchangeName = "topic-exchange";
    private static final String fanoutExchange = "fanout-exchange";
    private static final String headersExchange = "headers-exchange";

    private static final String queueName = "cord";

    /**
     * 声明队列
     */
    @Bean
    public Queue queue() {
        //Queue(String name, boolean durable, boolean exclusive, boolean autoDelete)
        return new Queue("cord", false, true, true);
    }

    /**
     * 声明Topic交换机
     *
     * @return
     */
    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(topicExchangeName);
    }

    /**
     * 将队列与Topic交换机进行绑定，并指定路由键
     *
     * @param queue
     * @param topicExchange
     * @return
     */
    @Bean
    Binding topicBinding(Queue queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with("org.cord.#");
    }

    /**
     * 声明fanout交换机
     *
     * @return
     */
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(fanoutExchange);
    }

    /**
     * 将队列与fanout交换机进行绑定
     *
     * @param queue
     * @param fanoutExchange
     * @return
     */
    @Bean
    Binding fanoutBinding(Queue queue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }

    /**
     * 声明Headers交换机
     *
     * @return
     */
    @Bean
    HeadersExchange headersExchange() {
        return new HeadersExchange(headersExchange);
    }

    /**
     * 将队列与headers交换机进行绑定
     *
     * @param queue
     * @param headersExchange
     * @return
     */
    @Bean
    Binding headersBinding(Queue queue, HeadersExchange headersExchange) {
        Map<String, Object> map = new HashMap<>();
        map.put("First", "A");
        map.put("Fourth", "D");
//        whereAny表示部分匹配，whereAll表示全部匹配
//        return BindingBuilder.bind(queue).to(headersExchange).whereAll(map).match();
        return BindingBuilder.bind(queue).to(headersExchange).whereAny(map).match();
    }

    /**
     * 根据环境变量注入RabbitMQ的工厂实例
     *
     * @return
     */
    @Bean("cbMQFactory")
    public CachingConnectionFactory cbRabbitMQConnFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setVirtualHost(System.getenv("CB_RABBITMQ_VIRTUALHOST"));
        connectionFactory.setAddresses(System.getenv("CB_RABBITMQ_ADDRESSES"));
        connectionFactory.setUsername(System.getenv("CB_RABBITMQ_USERNAME"));
        connectionFactory.setPassword(System.getenv("CB_RABBITMQ_PASSWORD"));

        return connectionFactory;
    }

    /**
     * 根据环境变量注入RabbitMQ的模板类实例
     *
     * @param cbMQFactory
     * @return
     */
    @Bean("cbRabbitTemplate")
    public RabbitTemplate cbRabbitTemplate(CachingConnectionFactory cbMQFactory) {
        RabbitTemplate template = new RabbitTemplate(cbMQFactory);
        template.setExchange(System.getenv("CB_RABBITMQ_EXCHANGE"));
        template.setRoutingKey(System.getenv("CB_RABBITMQ_ROUTINGKEY"));
        template.setDefaultReceiveQueue(System.getenv("CB_RABBITMQ_QUEUE"));

        return template;
    }
}
