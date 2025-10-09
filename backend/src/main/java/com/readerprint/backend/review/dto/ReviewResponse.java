package com.readerprint.backend.review.dto;


import com.querydsl.core.annotations.QueryProjection;
import com.readerprint.backend.rating.dto.RatingScoreDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@Getter
@NoArgsConstructor
public class ReviewResponse {
    private Long reviewId;
    private String title;
    private String author;
    private String publisher;
    private String content;
    private LocalDate readingStartDate;
    private LocalDate readingEndDate;

    @Setter
    private List<RatingScoreDto> ratingScores;


    @QueryProjection
    public ReviewResponse(Long reviewId, String title, String author, String publisher, String content, LocalDate readingStartDate, LocalDate readingEndDate) {
        this.reviewId = reviewId;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.content = content;
        this.readingStartDate = readingStartDate;
        this.readingEndDate = readingEndDate;
    }


}
