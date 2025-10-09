package com.readerprint.backend.common.error.exception;

import com.readerprint.backend.common.error.ErrorCode;

public class InternalServerException extends CommonException {

    public InternalServerException(ErrorCode errorCode) {
        super(errorCode);
    }
}