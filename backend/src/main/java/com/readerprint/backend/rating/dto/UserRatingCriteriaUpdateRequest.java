package com.readerprint.backend.rating.dto;

import java.util.List;

public record UserRatingCriteriaUpdateRequest(
        List<Long> criteriaIds
) {}
