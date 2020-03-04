package com.xingeyes.boot.messaging.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CallBackMQRequest {

    @NonNull
    private String msgCode;

    @NonNull
    private String name;

    @NonNull
    private String type;
    // TODO: 增加通用回调消息定义

    @Override
    public int hashCode(){
        // TODO:
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        // TODO:
        return super.equals(obj);
    }

    @Override
    public String toString() {
        // TODO:
        return super.toString();
    }
}
