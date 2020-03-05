package com.xingeyes.boot.security.spring.util;

import org.apache.http.HttpStatus;

public class XHttpResult {
    private int code = 200;
    private String msg;
    private Object data;

    public static XHttpResult error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }

    public static XHttpResult error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static XHttpResult error(int code, String msg) {
        XHttpResult r = new XHttpResult();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    public static XHttpResult ok(String msg) {
        XHttpResult r = new XHttpResult();
        r.setMsg(msg);
        return r;
    }

    public static XHttpResult ok(Object data) {
        XHttpResult r = new XHttpResult();
        r.setData(data);
        return r;
    }

    public static XHttpResult ok() {
        return new XHttpResult();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
