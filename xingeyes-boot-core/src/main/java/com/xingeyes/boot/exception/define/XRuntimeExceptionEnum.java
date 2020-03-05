package com.xingeyes.boot.exception.define;

/**
 * Xeyes Boot 运行时异常的常量定义
 * TODO: 扩充定义
 * 通常，来自三方应用的异常都定义为Xeyes Boot的运行时异常以便于后续处理
 */
public enum XRuntimeExceptionEnum {
    UNDEFINED_ERROR(-1),
    CONFIG_ERROR(0),
    REGISTER_ERROR(0),
    LOADBALANCE_ERROR(0),
    HTTP_ERROR(0),
    NETWORK_ERROR(0);

    // 内部异常码
    private Integer enumCode;

    XRuntimeExceptionEnum(Integer enumCode){
        this.enumCode = enumCode;
    }

    public Integer getEnumCode() {
        return enumCode;
    }

    public void setEnumCode(Integer enumCode){
        this.enumCode = enumCode;
    }
}
