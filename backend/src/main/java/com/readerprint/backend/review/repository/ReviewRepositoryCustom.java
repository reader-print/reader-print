package com.readerprint.backend.review.repository;


import com.readerprint.backend.review.dto.ReviewResponse;

import java.time.LocalDate;
import java.util.List;

public interface ReviewRepositoryCustom {
    List<ReviewResponse> findReviewByUser(Long userSeq, LocalDate startDate, LocalDate endDate);


}
