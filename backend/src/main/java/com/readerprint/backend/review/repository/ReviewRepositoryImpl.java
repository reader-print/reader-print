package com.readerprint.backend.review.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.readerprint.backend.book.entity.QBook;
import com.readerprint.backend.rating.dto.RatingScoreDto;
import com.readerprint.backend.rating.entity.QRatingCriteria;
import com.readerprint.backend.rating.entity.QRatingScore;
import com.readerprint.backend.review.dto.ReviewResponse;
import com.readerprint.backend.review.entity.QReview;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<ReviewResponse> findReviewByUser(Long userSeq, LocalDate startDate, LocalDate endDate) {
        QReview review = QReview.review;
        QBook book = QBook.book;

        // 리뷰 정보 조회
        List<ReviewResponse> reviews = jpaQueryFactory
                .select(Projections.constructor(ReviewResponse.class,
                        review.id,
                        book.title,
                        book.author,
                        book.publisher,
                        review.content,
                        review.readingStartDate,
                        review.readingEndDate))
                .from(review)
                .leftJoin(review.book, book)
                .where(
                        review.user.seq.eq(userSeq),
                        review.readingStartDate.goe(startDate),
                        review.readingEndDate.loe(endDate)
                )
                .fetch();

        // 각 리뷰에 대한 별점 정보 조회 및 매핑
        reviews.forEach(reviewResponse -> {
            List<RatingScoreDto> ratingScores = fetchRatingScores(reviewResponse.getReviewId());
            reviewResponse.setRatingScores(ratingScores);
        });

        return reviews;
    }

    private List<RatingScoreDto> fetchRatingScores(Long reviewId) {
        QRatingScore ratingScore = QRatingScore.ratingScore;
        QRatingCriteria criteria = QRatingCriteria.ratingCriteria;

        return jpaQueryFactory
                .select(Projections.constructor(RatingScoreDto.class,
                        criteria.name,
                        ratingScore.score))
                .from(ratingScore)
                .leftJoin(ratingScore.criteria, criteria)
                .where(ratingScore.review.id.eq(reviewId))
                .fetch();
    }
}
