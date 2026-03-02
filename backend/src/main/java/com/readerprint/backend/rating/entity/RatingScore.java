package com.readerprint.backend.rating.entity;

import com.readerprint.backend.review.entity.Review;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "rating_score")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class RatingScore {

    @EmbeddedId
    private RatingScoreId id;  // 복합 키 (review_id, criteria_id)

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("reviewId")
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("criteriaId")
    @JoinColumn(name = "criteria_id")
    private RatingCriteria criteria;

    @Column(nullable = false)
    private int score;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
