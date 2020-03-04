package com.xingeyes.boot.mongodb;

import com.mongodb.ConnectionString;
import com.xingeyes.boot.application.XEyesSpringContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;

@Configuration
public class MongoDBConfig {

    /**
     * 注入MongoDBFacotry 实例
     * @return
    */
    @Bean("mongoDbFactory")
    public MongoDbFactory getMongoDbFactory(){
        // TODO: 给出MongoDB的连接字符串（通常定义在application.yml(.porperties)里）
        ConnectionString uri = new ConnectionString(""); // TODO:
        // TODO: 如果需要定制化的连接工厂，这里可以替换为其它实现
        MongoDbFactory mongoDbFactory = new SimpleMongoClientDbFactory(uri);
        // TODO: 定制化的MongoDB连接工厂

        return mongoDbFactory;
    }

    /**
     * 注入MongoTemplate实例
     * @return
     */
    @Bean("mongoTemplate")
    public MongoTemplate getMongoTemplate(MongoDbFactory mongoDbFactory) {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory);
        mongoTemplate.setApplicationContext(XEyesSpringContext.getApplicationContext());
        //TODO: 定制化的模板规则
        return mongoTemplate;
    }
}
