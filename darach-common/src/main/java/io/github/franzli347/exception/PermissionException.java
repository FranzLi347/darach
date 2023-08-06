package io.github.franzli347.exception;

import io.github.franzli347.constant.ErrorCode;

public class PermissionException extends BusinessException{
    public PermissionException(int code, String message) {
        super(code, message);
    }

    public PermissionException(ErrorCode errorCode) {
        super(errorCode);
    }

    public PermissionException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    @Override
    public int getCode() {
        return super.getCode();
    }
}
