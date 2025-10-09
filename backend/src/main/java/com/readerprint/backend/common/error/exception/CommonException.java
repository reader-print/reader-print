package com.readerprint.backend.common.error.exception;

import com.readerprint.backend.common.error.ErrorCode;
import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {

    private final ErrorCode errorCode;

    public CommonException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}