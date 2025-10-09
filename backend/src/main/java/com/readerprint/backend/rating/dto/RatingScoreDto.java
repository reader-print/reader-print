package com.readerprint.backend.rating.dto;


import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RatingScoreDto {
    private String criteria;
    private int score;

    @QueryProjection
    public RatingScoreDto(String criteria, int score) {
        this.criteria = criteria;
        this.score = score;
    }
}
