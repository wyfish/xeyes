package com.xingeyes.boot.exception.define;

/**
 * Xeyes Boot通用异常常量定义
 * TODO: 扩充定义
 */
public enum XCommExceptionEnum {
    UNDEFINED_ERROR(-1),
    DUBBO_ERROR(0),
    NACOS_ERROR(0),
    JSON_ERROR(0),
    DB_CONNECTION_ERROR(0),
    REDIS_ERROR(0),
    KAFKA_ERROR(0),
    MONGODB_ERRR(0);

    // 内部异常码
    private Integer enumCode;

    XCommExceptionEnum(Integer enumCode){
        this.enumCode = enumCode;
    }

    public Integer getEnumCode() {
        return enumCode;
    }

    public void setEnumCode(Integer enumCode){
        this.enumCode = enumCode;
    }
}
