package com.xingeyes.boot.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 这个配置类应该被"XCacheConfig"类取代
 */
@Configuration
@EnableCaching
public class RedisConfig {

    @Bean("redisConnFactory")
    public RedisConnectionFactory getRedisConnectionFactory(){
        //Another instance
        //RedisConnectionFactory redisConnFactory = new LettuceConnectionFactory();
        RedisConnectionFactory redisConnFactory = new JedisConnectionFactory();
        return redisConnFactory;
    }

    @Bean("redisTemplate")
    @Autowired
    public RedisTemplate<String, Object> stringSerializerRedisTemplate(RedisConnectionFactory redisConnFactory) {

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(redisConnFactory);

        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        // key采用String的序列化方式
        redisTemplate.setKeySerializer(stringSerializer);
        // value序列化方式采用jackson
        redisTemplate.setValueSerializer(stringSerializer);
        // hash的key也采用String的序列化方式
        redisTemplate.setHashKeySerializer(stringSerializer);
        // hash的value序列化方式采用jackson
        redisTemplate.setHashValueSerializer(stringSerializer);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

}
