package com.xingeyes.boot.redis;


import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;

import java.lang.reflect.Method;

public class RedisKeyGenerator implements KeyGenerator {

    private static final String PREFIX = "XEYES_";
    public static final String PREFIX_PATTERN = "*XEYES*";

    /**
     * 扩展简单KEY产生器，增加应用标识"XEYES_"
     * @param target
     * @param method
     * @param params
     * @return
     */
    @Override
    public Object generate(Object target, Method method, Object... params){

        Class[] interfaces = target.getClass().getInterfaces();
        String className;
        if(interfaces == null || interfaces.length ==0 ){
            className = target.getClass().getSimpleName();
        }else {
            className = interfaces[0].getSimpleName();
        }
        String paramValue;
        if (params.length == 0) {
            paramValue = StringUtils.EMPTY;
        }else {
            paramValue = SimpleKeyGenerator.generateKey(params).toString();
        }

        StringBuilder stringBuilder = new StringBuilder(PREFIX);
        stringBuilder.append(className).append(method.getName()).append(paramValue);
        return stringBuilder.toString();
    }
}
