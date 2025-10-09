package com.readerprint.backend.review.service;

import com.readerprint.backend.review.dto.ReviewResponse;
import com.readerprint.backend.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public List<ReviewResponse> getMyReviews(LocalDate startDate, LocalDate endDate, Long userSeq) {
        return reviewRepository.findReviewByUser(userSeq, startDate, endDate);
    }
}
