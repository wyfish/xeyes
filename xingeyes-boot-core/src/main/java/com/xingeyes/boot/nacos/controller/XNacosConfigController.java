package com.xingeyes.boot.nacos.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Nacos 配置中心的使用示例
 * Note： @NacosPropertySource(dataId="xxxx", autoRefreshed=true) 应该加到主程序入口处
 *        "xxxx"为Nacos管理台配置的dataId
 */
@RestController
@RequestMapping("nacos-config")  // 示例路径
public class XNacosConfigController {

    // 获取配置中心的属性值
    @NacosValue(value = "${hello}", autoRefreshed = true)
    private String hello;

    @GetMapping(value="/hello")
    public String get(){
        return hello;
    }
}
