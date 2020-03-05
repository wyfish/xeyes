package com.xingeyes.boot.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * 用于获取属性文件中的各类属性
 */
@Service
public class ReloadableProperties {

    private static final String[] EMPTY_STRING_ARRAY = new String[0];
    private static final Integer[] EMPTY_INTEGER_ARRAY = new Integer[0];
    private static final Long[] EMPTY_LONG_ARRAY = new Long[0];

    @Autowired
    public PropertiesFactoryBean propertiesFactoryBean;

    /**
     *
     * @param key
     * @return
     */
    public String getString(String key) {
        return getProperties().getProperty(key);
    }

    /**
     * 根据Key获取属性值，如果没有找到，则返回给定的值
     * @param key
     * @param value
     * @return
     */
    public int getInt(String key, int value){
        String valueStr = getProperties().getProperty(key);
        if (valueStr == null){
            return value;
        }
        try {
            return Integer.valueOf(valueStr);
        }catch(Exception e){
            return value;
        }
    }

    public int getInt(String key){
        return getInt(key, 0);
    }

    /**
     * 根据分隔符返回属性值数组
     * @param key
     * @return
     */
    public String[] getStringArray(String key){
        String result = getString(key);
        if (result == null){
            return EMPTY_STRING_ARRAY;
        }
        return result.split(",");
    }

    /**
     *
     * @param key
     * @return
     */
    public List<String> getStringList(String key){
        String [] stringArray = getStringArray(key);
        return Arrays.asList(stringArray);
    }

    /**
     *
     * @param key
     * @return
     */
    public Integer[] getIntegerArray(String key){
        String result = getString(key);
        if (result == null){
            return EMPTY_INTEGER_ARRAY;
        }
        String [] stringArray = result.split(",");
        Integer[] resultArray = new Integer[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            resultArray[i] = Integer.parseInt(stringArray[i]);
        }
        return resultArray;
    }

    /**
     * 获取布尔类型的属性值
     * @param key
     * @return
     */
    public boolean getBoolean (String key){
        String flag = "false";

        try{
            String propFlag = getString(key);
            if (StringUtils.isNoneEmpty(propFlag) &&
                    Boolean.TRUE.toString().equalsIgnoreCase(propFlag)){
                flag = propFlag;
            }
        }catch (IllegalStateException e){
            // TODO:
        }
        return Boolean.valueOf(flag);
    }

    private Properties getProperties(){
        Properties prop;
        try{
            prop = this.propertiesFactoryBean.getObject();
        }catch (IOException e)
        {
            throw new RuntimeException("Can not get the System Properties.", e);
        }
        return prop;
    }
}
