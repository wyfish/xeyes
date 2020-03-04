package com.xingeyes.boot.elasticsearch;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import javax.annotation.PostConstruct;

/**
 * Spring-Boot 的ElasticSearch的配置类,
 */
@Configuration
public class XElasticSearchConfig {

    /**
     * 防止netty的bug
     * java.lang.IllegalStateException: availableProcessors is already set to [4], rejecting [4]
     */
    @PostConstruct
    public void init() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }

    /**
     * 注入自定的 ElasticsearchTemplate实例
     * @return
     */
    @Bean("elasticsearchTemplate")
    public ElasticsearchTemplate elasticsearchTemplate(){

        // ElasticsearchTemplate elasticsearchTemplate = new ElasticsearchTemplate(new PreBuiltTransportClient());
        return null;
    }
}
