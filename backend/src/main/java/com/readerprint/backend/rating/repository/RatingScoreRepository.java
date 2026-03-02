package com.readerprint.backend.rating.repository;

import com.readerprint.backend.rating.entity.RatingScore;
import com.readerprint.backend.rating.entity.RatingScoreId;
import com.readerprint.backend.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingScoreRepository extends JpaRepository<RatingScore, RatingScoreId> {
    void deleteByReview(Review review);
}
