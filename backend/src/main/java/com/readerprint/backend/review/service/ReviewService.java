package com.readerprint.backend.review.service;

import com.readerprint.backend.book.entity.Book;
import com.readerprint.backend.book.repository.BookRepository;
import com.readerprint.backend.common.error.ErrorCode;
import com.readerprint.backend.common.error.exception.BadRequestException;
import com.readerprint.backend.rating.dto.RatingScoreDto;
import com.readerprint.backend.rating.entity.RatingCriteria;
import com.readerprint.backend.rating.entity.RatingScore;
import com.readerprint.backend.rating.entity.RatingScoreId;
import com.readerprint.backend.rating.repository.RatingCriteriaRepository;
import com.readerprint.backend.rating.repository.RatingScoreRepository;
import com.readerprint.backend.review.dto.ReviewCreateResponse;
import com.readerprint.backend.review.dto.ReviewRequest;
import com.readerprint.backend.review.dto.ReviewResponse;
import com.readerprint.backend.review.entity.Review;
import com.readerprint.backend.review.repository.ReviewRepository;
import com.readerprint.backend.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final RatingCriteriaRepository ratingCriteriaRepository;
    private final RatingScoreRepository ratingScoreRepository;

    public List<ReviewResponse> getMyReviews(LocalDate startDate, LocalDate endDate, Long userSeq) {
        return reviewRepository.findReviewByUser(userSeq, startDate, endDate);
    }

    @Transactional
    public ReviewCreateResponse createReview(ReviewRequest request, User user){
        Book book = bookRepository.findByIsbn(request.isbn())
                .orElseGet(() -> bookRepository.save(Book.builder()
                        .isbn(request.isbn())
                        .title(request.title())
                        .author(request.author())
                        .publisher(request.publisher())
                        .genre(request.genre())
                        .build()));

        if (reviewRepository.existsByUserAndBook(user, book)) {
            throw new BadRequestException(ErrorCode.REVIEW_ALREADY_EXISTS);
        }

        Review saved = reviewRepository.save(Review.builder()
                .user(user)
                .book(book)
                .content(request.content())
                .readingStartDate(request.readingStartDate())
                .readingEndDate(request.readingEndDate())
                .build());

        saveRatingScores(saved, request.ratingScores());

        return new ReviewCreateResponse(saved.getId());
    }

    @Transactional
    public ReviewCreateResponse updateReview(Long reviewId, ReviewRequest request, User user) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.REVIEW_NOT_FOUND));

        if (!review.getUser().getSeq().equals(user.getSeq())) {
            throw new BadRequestException(ErrorCode.FORBIDDEN);
        }

        review.update(request.content(), request.readingStartDate(), request.readingEndDate());

        ratingScoreRepository.deleteByReview(review);
        saveRatingScores(review, request.ratingScores());

        return new ReviewCreateResponse(review.getId());
    }

    private void saveRatingScores(Review review, List<RatingScoreDto> ratingScores) {
        if (ratingScores == null) return;

        for (RatingScoreDto dto : ratingScores) {
            RatingCriteria criteria = ratingCriteriaRepository.findByName(dto.getCriteria())
                    .orElseThrow(() -> new BadRequestException(ErrorCode.CRITERIA_NOT_FOUND));

            ratingScoreRepository.save(RatingScore.builder()
                    .id(new RatingScoreId(review.getId(), criteria.getId()))
                    .review(review)
                    .criteria(criteria)
                    .score(dto.getScore())
                    .createdAt(LocalDateTime.now())
                    .build());
        }
    }
}
