package io.github.franzli347.exception;

import io.github.franzli347.constant.ErrorCode;

public class LoginStatusException extends BusinessException{
    public LoginStatusException(int code, String message) {
        super(code, message);
    }

    public LoginStatusException(ErrorCode errorCode) {
        super(errorCode);
    }

    public LoginStatusException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    @Override
    public int getCode() {
        return super.getCode();
    }
}
