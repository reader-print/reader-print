package com.readerprint.backend.review.dto;

import com.readerprint.backend.rating.dto.RatingScoreDto;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.List;

public record ReviewRequest(
        String isbn,
        @NotBlank String title,
        String author,
        String publisher,
        String genre,
        String content,
        List<RatingScoreDto> ratingScores,
        LocalDate readingStartDate,
        LocalDate readingEndDate
) {
}
