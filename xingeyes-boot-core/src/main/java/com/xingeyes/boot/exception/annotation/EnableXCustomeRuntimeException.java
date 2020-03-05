package com.xingeyes.boot.exception.annotation;

import com.xingeyes.boot.exception.handler.XExceptionHandler;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在主程序入口通过打开此注解来实现异常处理类的实例化和注入
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(XExceptionHandler.class) // 这里通过导入来实现Bean的自定义注入需实现 ImportBeanDefinitionRegistrar 接口
@Documented
public @interface EnableXCustomeRuntimeException {
    /**
     * 是否开启
     * @return
     */
    boolean enabled() default true;
}
