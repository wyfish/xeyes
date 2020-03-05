package com.xingeyes.boot.exception;

import com.xingeyes.boot.exception.define.XRuntimeExceptionEnum;

/**
 * Xeyes Boot 自定义的运行时异常
 */
public class XCustomeRuntimeException extends RuntimeException {

    /**
     * 异常编码
     */
    private Integer code;

    /**
     *
     */
    public XCustomeRuntimeException(){
        super();
        this.code = XRuntimeExceptionEnum.UNDEFINED_ERROR.getEnumCode();
    }

    /**
     *
     * @param code
     */
    public XCustomeRuntimeException(Integer code){
        super();
        this.code = code;
    }

    /**
     *
     * @param msg
     */
    public XCustomeRuntimeException(String msg){
        super(msg);
        this.code = XRuntimeExceptionEnum.UNDEFINED_ERROR.getEnumCode();
    }

    /**
     *
     * @param msg
     * @param code
     */
    public XCustomeRuntimeException(String msg, Integer code){
        super(msg);
        this.code = code;
    }

    /**
     *
     * @param message
     * @param cause
     */
    public XCustomeRuntimeException(String message, Throwable cause) {

        super(message, cause);
        this.code = XRuntimeExceptionEnum.UNDEFINED_ERROR.getEnumCode();
    }

    /**
     *
     * @param message
     * @param cause
     * @param code
     */
    public XCustomeRuntimeException(String message, Throwable cause, Integer code) {

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
    protected XCustomeRuntimeException(String message, Throwable cause,
                             boolean enableSuppression,
                             boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
