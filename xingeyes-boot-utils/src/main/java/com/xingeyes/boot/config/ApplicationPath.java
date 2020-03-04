package com.xingeyes.boot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
public class ApplicationPath {

    private String confPath;

    public String getConfFilePath(String filePath) {
        return System.getenv("APP_HOME_CONF" + filePath);
    }

}
