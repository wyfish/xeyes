package com.xingeyes.boot.security.oauth2.client2;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextListener;

/**
 * 单点登录系统客户端示例： 主程序入口
 * 客户端1，和2 的端口：8882，8883
 *
 */
@SpringBootApplication
public class XOAuth2ClientApplication extends SpringBootServletInitializer {

    // 启动类需要添加 RequestContextListener，用于监听HTTP请求事件。
    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

    public static void main(String[] args) {
        SpringApplication.run(XOAuth2ClientApplication.class, args);
    }
}
