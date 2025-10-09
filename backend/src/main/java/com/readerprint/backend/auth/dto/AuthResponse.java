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
    private String refreshToken;
    private LocalDateTime blockedUntil;  // 일반 사용자만 - 블락 정보
    private String message;

    public static AuthResponse success(String userId, String email, String nickname,
                                       Role role, String accessToken, String refreshToken, LocalDateTime blockedUntil, String message) {
        return AuthResponse.builder()
                .userId(userId)
                .email(email)
                .nickname(nickname)
                .role(role)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .blockedUntil(blockedUntil)
                .message(message)
                .build();
    }

}
