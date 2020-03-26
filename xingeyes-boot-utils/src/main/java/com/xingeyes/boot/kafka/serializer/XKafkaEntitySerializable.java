package com.xingeyes.boot.kafka.serializer;

import com.xingeyes.boot.kafka.entity.XKafkaEntityObject;
import org.apache.kafka.common.serialization.Serializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

/**
 * 序列化器
 * 用法：
 * 修改生产者配置的value-serializer
 * spring.kafka.producer.value-serializer=com.xingeyes.boot.kafka.serializer.XKafkaEntitySerializable
 * 但是更好的做法是使用Json串来序列与反序列化
 */
public class XKafkaEntitySerializable implements Serializer<XKafkaEntityObject> {
    @Override
    public void configure(Map<String, ?> map, boolean b) {
        //TODO:
    }

    @Override
    public byte[] serialize(String topic, XKafkaEntityObject object) {
        System.out.println("topic : " + topic + ", object : " + object);
        byte[] dataArray = null;
        ByteArrayOutputStream outputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(object);
            dataArray = outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(objectOutputStream != null){
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return dataArray;
    }

    @Override
    public void close() {
        //TODO:
    }
}
