package com.xingeyes.boot.caching;


import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * Spring-Boot缓存配置类，使用Redis作为默认的缓存
 * Spring Cache接口为缓存的组件规范定义，包含缓存的各种操作集合，并提供了各种xxxCache的实现，
 * 如RedisCache，EhCacheCache,ConcurrentMapCache等；
 *
 * @CacheConfig: 设置类级别上共享的一些常见缓存设置
 * @Cacheable: 触发缓存写入
 * @CacheEvict: 触发缓存清除
 * @Caching: 将多种缓存操作分组
 * @CachePut: 更新缓存(不会影响到方法的运行)
 *
 * @EnableCaching注解是 Spring Framework中的注解驱动的缓存管理功能，当你在配置类(@Configuration)上使用@EnableCaching注解时，
 * 会触发一个post processor，这会扫描每一个Spring bean，查看是否已经存在注解对应的缓存。如果找到了，
 * 就会自动创建一个代理拦截方法调用，使用缓存的bean执行处理。
 */
@Configuration
@EnableCaching
public class XCacheConfig {

    /**
     * 注入Lettuce Connection Factory实例
     *
     * @return
     */
    @Bean("redisConnFactory")
    public RedisConnectionFactory getRedisConnectionFactory() {

        //Redis支持的两种连接池配置(需要与application.yml属性文件中的一致)
        RedisConnectionFactory redisConnFactory = new LettuceConnectionFactory();
        //RedisConnectionFactory redisConnFactory = new JedisConnectionFactory();
        return redisConnFactory;
    }

    /**
     * 配置缓存管理器
     *
     * @param redisConnFactory
     * @return
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnFactory) {
        // 在这里，可以根据不同的配置，生成多种默认的缓存配置
        RedisCacheConfiguration cacheConfig1 = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))
                .prefixKeysWith("cache:user:")  // TODO: 实际根据需求更改
                .disableCachingNullValues()
                .serializeKeysWith((keyPair()))
                .serializeValuesWith(valuePair());

        RedisCacheConfiguration cacheConfig2 = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(30))
                .prefixKeysWith("cache:userInfo:")  // TODO: 实际根据需求更改
                .disableCachingNullValues()
                .serializeKeysWith((keyPair()))
                .serializeValuesWith(valuePair());
        // 返回Redis缓存管理器
        return RedisCacheManager.builder(redisConnFactory)
                .withCacheConfiguration("user",cacheConfig1)
                .withCacheConfiguration("userInfo",cacheConfig2)
                .build();
    }

    @Bean("redisTemplate")
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

    /**
     * 配置键值序列化
     * @return
     */
    private RedisSerializationContext.SerializationPair<String> keyPair(){
        return RedisSerializationContext
                .SerializationPair
                .fromSerializer(new StringRedisSerializer());
    }

    /**
     * 配置值序列化，使用GenericJackson2JsonRedisSerializer替换默认序列化
     * @return
     */
    private RedisSerializationContext.SerializationPair<Object> valuePair(){
        return RedisSerializationContext
                .SerializationPair
                .fromSerializer(new GenericJackson2JsonRedisSerializer());
    }
}