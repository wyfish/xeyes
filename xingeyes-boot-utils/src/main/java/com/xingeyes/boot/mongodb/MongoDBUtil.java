package com.xingeyes.boot.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * 封装MongoTemplate类，对外屏蔽MongoDB访问
 */
@Service
public class MongoDBUtil {

    @Autowired
    private MongoTemplate mongoTemplate;

    // TODO: 增加具体的调用函数逻辑

}
