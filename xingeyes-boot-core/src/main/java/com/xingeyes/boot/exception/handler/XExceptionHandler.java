package com.xingeyes.boot.exception.handler;

import com.xingeyes.boot.exception.XCustomeRuntimeException;
import com.xingeyes.boot.exception.annotation.EnableXCustomeRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.ServletException;
import java.sql.SQLException;

/**
 * 定义Xeyes Boot全局的异常处理类
 */
public class XExceptionHandler extends ResponseEntityExceptionHandler implements ImportBeanDefinitionRegistrar {

    private Logger logger = LoggerFactory.getLogger(XExceptionHandler.class);

    /**
     * 重载 ResponseEntityExceptionHandler#handleExceptionInternal
     * @param ex
     * @param body
     * @param headers
     * @param status
     * @param webRequest
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
        logger.warn("{}", ex.getMessage());

        // TODO: 增加自定义代码逻辑
        return super.handleExceptionInternal(ex,body,headers,status,webRequest);
    }

    /**
     * 处理 Xeyes Boot自定义运行时异常
     * @param ex
     * @return
     */
    @ExceptionHandler(value = {XCustomeRuntimeException.class})
    protected final ResponseEntity<Object> handleXCustomRuntimeException(XCustomeRuntimeException ex){
        // TODO:

        return null;
    }

    /**
     * 处理Servlet 异常
     * @param ex
     * @return
     */
    @ExceptionHandler(value = {ServletException.class})
    protected final ResponseEntity<Object> handleServletException(ServletException ex){
        // TODO:

        return null;
    }

    /**
     * 处理DB SQL异常
     * @param ex
     * @return
     */
    @ExceptionHandler(value = {SQLException.class})
    protected final ResponseEntity<Object> handleSQLException(SQLException ex){
        // TODO:

        return null;
    }

    /**
     * 处理运行时异常
     * @param ex
     * @return
     */
    @ExceptionHandler(value = {RuntimeException.class})
    protected final ResponseEntity<Object> handleRuntimeException(RuntimeException ex){
        // TODO:

        return null;
    }

    /**
     * 实现ImportBeanDefinitionRegistrar接口，以便自定义Bean的导入与实例化
     * @param annotationMetadata
     * @param beanDefinitionRegistry
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        //是否含有@EnableCustomException注解
        if (annotationMetadata.isAnnotated(EnableXCustomeRuntimeException.class.getName())){
            //获取该注解上面的所有属性，然后封装成一个map
            MultiValueMap<String, Object> attributes = annotationMetadata.getAllAnnotationAttributes(EnableXCustomeRuntimeException.class.getName());
            // 根据注解的取值来判断是否注入通常@import注解导入的类
            // TODO:
            // if(attributes.get(EnableXCustomeRuntimeException.enabled()).equals(Boolean.TRUE)){
            if (true){
                BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(XExceptionHandler.class);
                beanDefinitionRegistry.registerBeanDefinition(XExceptionHandler.class.getName(),beanDefinitionBuilder.getBeanDefinition());
            }
        }
    }
}
