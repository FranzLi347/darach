package io.github.franzli347.exception;


import io.github.franzli347.constant.ErrorCode;

public class XxlJobException extends BusinessException{

    public XxlJobException(int code, String message) {
        super(code, message);
    }

    public XxlJobException(ErrorCode errorCode) {
        super(errorCode);
    }

    public XxlJobException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
