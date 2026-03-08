package com.readerprint.backend.rating.dto;

import jakarta.validation.constraints.NotBlank;

public record RatingCriteriaCreateRequest(
        @NotBlank String name,
        String description
) {}
