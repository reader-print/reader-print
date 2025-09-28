package com.readerprint.backend.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_detail")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetail {
    // 일반 사용자

    @Id
    @Column(name = "user_seq")
    private Long userSeq;

    @OneToOne
    @MapsId  // 외래키를 기본키로 사용
    @JoinColumn(name = "user_seq")
    private User user;

    @Builder.Default
    @Column(name = "sns_linked")
    private Boolean snsLinked = false;

    @Builder.Default
    @Column(name = "block_count")
    private Integer blockCount = 0;

    @Column(name = "blocked_until")
    private LocalDateTime blockedUntil;

    @Builder.Default
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 블락 상태 체크
    public boolean isBlocked() {
        return blockedUntil != null && LocalDateTime.now().isBefore(blockedUntil);
    }


}
