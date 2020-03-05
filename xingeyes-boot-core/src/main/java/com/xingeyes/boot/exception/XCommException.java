package com.xingeyes.boot.exception;

import com.xingeyes.boot.exception.define.XCommExceptionEnum;

/**
 * Xeyes Boot 通用异常封装
 * TODO: 增加针对不同平台，不同三方，不同应用的错误码逻辑
 */
public class XCommException extends Exception {

    /**
     * 异常编码
     */
    private Integer code;

    /**
     *
     */
    public XCommException(){
       super();
       this.code = XCommExceptionEnum.UNDEFINED_ERROR.getEnumCode();
    }

    /**
     *
     * @param code
     */
    public XCommException(Integer code){
        super();
        this.code = code;
    }

    /**
     *
     * @param msg
     */
    public XCommException(String msg){
        super(msg);
        this.code = XCommExceptionEnum.UNDEFINED_ERROR.getEnumCode();
    }

    /**
     *
     * @param msg
     * @param code
     */
    public XCommException(String msg, Integer code){
        super(msg);
        this.code = code;
    }

    /**
     *
     * @param message
     * @param cause
     */
    public XCommException(String message, Throwable cause) {

        super(message, cause);
        this.code = XCommExceptionEnum.UNDEFINED_ERROR.getEnumCode();
    }

    /**
     *
     * @param message
     * @param cause
     * @param code
     */
    public XCommException(String message, Throwable cause, Integer code) {

        super(message, cause);
        this.code = code;
    }


    /**
     *
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    protected XCommException(String message, Throwable cause,
                        boolean enableSuppression,
                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = XCommExceptionEnum.UNDEFINED_ERROR.getEnumCode();
    }
}
