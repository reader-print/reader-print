package com.readerprint.backend.user.dto;

import com.readerprint.backend.user.entity.User;

import java.time.LocalDateTime;

public record AdminUserResponse(
        String userId,
        String email,
        Boolean isSnsLinked,
        Integer blockCount,
        LocalDateTime blockedUntil,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static AdminUserResponse from(User user) {
        boolean hasSnsLinked = user.getUserDetail() != null && Boolean.TRUE.equals(user.getUserDetail().getSnsLinked());
        int blockCount = user.getUserDetail() != null ? user.getUserDetail().getBlockCount() : 0;
        LocalDateTime blockedUntil = user.getUserDetail() != null ? user.getUserDetail().getBlockedUntil() : null;

        return new AdminUserResponse(
                user.getUserId(),
                user.getEmail(),
                hasSnsLinked,
                blockCount,
                blockedUntil,
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
