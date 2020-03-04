package com.xingeyes.boot.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.util.Set;

public class RedisClearCache implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(RedisClearCache.class);

    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event){

    }

    /**
     * 清空Xeyes平台应用缓存
     */
    public void clearCache() {
        Set<byte[]> keys = connectionFactory.getConnection().keys(RedisKeyGenerator.PREFIX_PATTERN.getBytes());
        keys.forEach( e-> {
            connectionFactory.getConnection().del(e);
        });
        logger.info("Redis cache has been cleared.");
    }
}
