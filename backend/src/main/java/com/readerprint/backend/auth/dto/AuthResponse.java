package com.readerprint.backend.auth.dto;

import com.readerprint.backend.user.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AuthResponse {
    private String userId;
    private String email;
    private String nickname;
    private Role role;
    private String accessToken;
    private LocalDateTime blockedUntil;  // 일반 사용자만 - 블락 정보
    private String message;

    // 성공 응답용 생성자
    public static AuthResponse success(String userId, String email, String nickname,
                                       Role role, String accessToken, LocalDateTime blockedUntil, String message) {
        return AuthResponse.builder()
                .userId(userId)
                .email(email)
                .nickname(nickname)
                .role(role)
                .accessToken(accessToken)
                .blockedUntil(blockedUntil)
                .message(message)
                .build();
    }

    // 실패 응답용 생성자
    public static AuthResponse failure(String message) {
        return AuthResponse.builder()
                .message(message)
                .build();
    }

}
