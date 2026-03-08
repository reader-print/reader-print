package com.readerprint.backend.rating.dto;

public record RatingCriteriaUpdateRequest(
        String name,
        String description,
        Boolean isActivate
) {}
