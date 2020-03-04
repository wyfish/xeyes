package com.xingeyes.boot.json.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class XJacksonJsonUtil {

    private static final Logger logger = LoggerFactory.getLogger(XJacksonJsonUtil.class);
    private static final String EMPTY_JSON = "{}";
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
    }

    private XJacksonJsonUtil(){}

    /**
     *
     * @param obj
     * @return
     */
    public static String format(Object obj){
        try{
            return mapper.writeValueAsString(obj);
        }catch (JsonProcessingException e) {
            logger.error("TODO");
            return EMPTY_JSON;
        }
    }

    /**
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T parse(String json, Class<T> clazz){
        try{
            return mapper.readValue(json,clazz);
        }catch (IOException e) {
            logger.error("TODO");
            try{
                return clazz.newInstance();
            }catch (InstantiationException | IllegalAccessException ex){
                logger.error("TODO");
                return null;
            }
        }
    }

    /**
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> parseToList (String json, Class<T> clazz){
        try{
            return mapper.readValue(json, mapper.getTypeFactory().constructParametricType(ArrayList.class, clazz));
        }catch(IOException e){
            logger.error("TODO:");
            return null;
        }
    }

    public static boolean isEmptyJSon(String json) {
        return EMPTY_JSON.equals(json);
    }
}
