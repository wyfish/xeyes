package com.xingeyes.boot.scheduler.listener;

import org.quartz.ee.servlet.QuartzInitializerListener;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;

/**
 * 监听器实例，扩展QuartzInitializerListener实现
 * QuartzInitializerListener 实现了 ServletContextListener接口，用于监听MVC框架
 */
@Component
public class XQuartzListener extends QuartzInitializerListener {

    /**
     *
     * @param sce
     */
    public void contextInitialized(ServletContextEvent sce) {
        super.contextInitialized(sce);
        // TODO: 增加代码处理程序启动时DB的变代和加载

    }

    /**
     *
     * @param sce
     */
    public  void contextDestroyed(ServletContextEvent sce) {
        super.contextDestroyed(sce);
        // TODO: 增加代码逻辑，如果需要
    }

}
