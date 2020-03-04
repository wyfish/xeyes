package com.xingeyes.boot.json;

import java.util.List;

public interface XJsonUtil {

    /**
     *
     * @param obj
     * @return
     */
    public String format(Object obj);

    /**
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T parse(String json, Class<T> clazz);

    /**
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T> parseToList (String json, Class<T> clazz);
}
