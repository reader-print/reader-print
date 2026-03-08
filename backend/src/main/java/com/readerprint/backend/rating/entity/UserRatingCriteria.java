package com.readerprint.backend.rating.entity;

import com.readerprint.backend.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_rating_criteria")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserRatingCriteria {

    @EmbeddedId
    private UserRatingCriteriaId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userSeq")
    @JoinColumn(name = "user_seq")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("criteriaId")
    @JoinColumn(name = "criteria_id")
    private RatingCriteria criteria;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
