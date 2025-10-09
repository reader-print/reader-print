package com.readerprint.backend.common.error;


import com.readerprint.backend.common.error.exception.CommonException;
import com.readerprint.backend.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice(annotations = RestController.class)
@Slf4j
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ApiResponse<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException", e);

        return ApiResponse.of(HttpStatus.BAD_REQUEST, e.getBindingResult().getAllErrors().getFirst().getDefaultMessage(), null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiResponse<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("handleHttpMessageNotReadableException", e);

        return ApiResponse.of(HttpStatus.BAD_REQUEST, ErrorCode.BAD_REQUEST_ERROR.getMessage(), null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ApiResponse<Object> handleBindException(BindException e) {
        log.error("handleBindException", e);

        return ApiResponse.of(HttpStatus.BAD_REQUEST, e.getBindingResult().getAllErrors().getFirst().getDefaultMessage(), null
        );
    }

    @ExceptionHandler
    public ApiResponse<Object> handleCommonException(CommonException e) {
        log.error("handleCommonException", e);

        return ApiResponse.error(e.getErrorCode());
    }
}