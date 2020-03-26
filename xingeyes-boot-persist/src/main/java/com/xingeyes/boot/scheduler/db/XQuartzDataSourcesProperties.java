package com.xingeyes.boot.scheduler.db;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 添加一个properties，通过Spring自动注入配置数据源参数
 */
@Component
@ConfigurationProperties(prefix = "spring.datasource")
@Setter
@Getter
public class XQuartzDataSourcesProperties {

    private String url;

    private String driverClassName;

    private String username;

    private String password;
}
