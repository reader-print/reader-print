package com.readerprint.backend.common.error.exception;

import com.readerprint.backend.common.error.ErrorCode;

public class BadRequestException extends CommonException {

    public BadRequestException(ErrorCode errorCode) {
        super(errorCode);
    }
}