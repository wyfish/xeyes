package com.xingeyes.boot.kafka.deserializer;

import com.xingeyes.boot.kafka.entity.XKafkaEntityObject;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;

/**
 * 反序列化器
 * 用法：
 * 修改消费者配置的value-deserializer
 * spring.kafka.consumer.value-serializer=com.xingeyes.boot.kafka.deserializer.XKafkaEntityDeserializer
 * 但是更好的做法是使用Json串来序列与反序列化
 */
public class XKafkaEntityDeserializer implements Deserializer<XKafkaEntityObject> {

    @Override
    public void configure(Map<String, ?> map, boolean b) {
        //TODO:
    }

    @Override
    public XKafkaEntityObject deserialize(String s, byte[] bytes) {
        //TODO: 增加反序列化代码
        XKafkaEntityObject object = null;
        ByteArrayInputStream inputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            inputStream = new ByteArrayInputStream(bytes);
            objectInputStream = new ObjectInputStream(inputStream);
            object = (XKafkaEntityObject)objectInputStream.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(objectInputStream != null){
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return object;
    }

    @Override
    public void close() {
        //TODO:
    }
}
