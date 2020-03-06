package com.xingeyes.boot.security.oauth2.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * 单点登录系统服务端示例: 主程序访问入口
 * 说明：
 * 当通过任意客户端访问资源服务器受保护的接口时，会跳转到认证服务器的统一登录界面，要求登录，
 * 登录之后，在登录有效时间内任意客户端都无需再登录。
 *
 * 通常单点登录的整体流程如下：
 * 1. 浏览器向UI服务器点击触发要求安全认证
 * 2. 跳转到授权服务器获取授权许可码
 * 3. 从授权服务器带授权许可码跳回来
 * 4. UI服务器向授权服务器获取AccessToken
 * 5. 返回AccessToken到UI服务器
 * 6. 发出/resource请求到UI服务器
 * 7. UI服务器将/resource请求转发到Resource服务器
 * 8. Resource服务器要求安全验证,于是直接从授权服务器获取认证授权信息进行判断后(最后会响应给UI服务器,UI服务器再响应给浏览中器）
 */
@SpringBootApplication
@EnableResourceServer
public class XOAuthServerApplication extends SpringBootServletInitializer {

    /**
     * 主程序
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(XOAuthServerApplication.class, args);
    }
}
