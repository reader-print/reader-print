package com.readerprint.backend.rating.dto;

import com.readerprint.backend.rating.entity.RatingCriteria;

public record RatingCriteriaResponse(
        Long criteriaId,
        String name,
        String description,
        Boolean isActivate
) {
    public static RatingCriteriaResponse from(RatingCriteria criteria) {
        return new RatingCriteriaResponse(
                criteria.getId(),
                criteria.getName(),
                criteria.getDescription(),
                criteria.getIsActivate()
        );
    }
}
