package com.xingeyes.boot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * 该类用于统一指定整个应用配置的起始配置文件路径
 */
@Component
@Setter
@Getter
public class ApplicationPath {

    private String confPath;

    public String getConfFilePath(String filePath) {
        return System.getenv("APP_HOME_CONF" + filePath);
    }

}
