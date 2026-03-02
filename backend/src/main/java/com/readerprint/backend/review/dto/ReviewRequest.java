package com.readerprint.backend.review.dto;

import com.readerprint.backend.rating.dto.RatingScoreDto;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record ReviewRequest(
        @NotNull Long bookId,
        String content,
        List<RatingScoreDto> ratingScores,
        LocalDate readingStartDate,
        LocalDate readingEndDate
) {
}
