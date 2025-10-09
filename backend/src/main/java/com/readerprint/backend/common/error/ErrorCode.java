package com.readerprint.backend.common.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // 공통
    BAD_REQUEST_ERROR(HttpStatus.BAD_REQUEST, "요청값이 잘못되었습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"서버 오류가 발생했습니다."),


    // Authentication, Authorization
    INVALID_CREDENTIALS(HttpStatus.BAD_REQUEST,"이메일 또는 비밀번호가 올바르지 않습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED,  "인증이 필요합니다."),
    WRONG_TYPE_TOKEN(HttpStatus.UNAUTHORIZED,"잘못된 타입의 토큰입니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    LOGIN_FAILED(HttpStatus.BAD_REQUEST, "로그인 처리 중 오류가 발생했습니다."),

    // 사용자
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"사용자를 찾을 수 없습니다."),
    USER_BLOCKED(HttpStatus.FORBIDDEN, "차단된 사용자입니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT,  "이미 사용 중인 이메일입니다."),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT,  "이미 사용 중인 이메일입니다."),
    DUPLICATE_USER_ID(HttpStatus.CONFLICT,  "이미 사용 중인 아이디입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
