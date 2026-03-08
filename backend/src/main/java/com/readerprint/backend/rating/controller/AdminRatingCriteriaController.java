package com.readerprint.backend.rating.controller;

import com.readerprint.backend.common.response.ApiResponse;
import com.readerprint.backend.rating.dto.RatingCriteriaCreateRequest;
import com.readerprint.backend.rating.dto.RatingCriteriaResponse;
import com.readerprint.backend.rating.dto.RatingCriteriaUpdateRequest;
import com.readerprint.backend.rating.service.AdminRatingCriteriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "관리자 별점기준 관리")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/rating-criteria")
public class AdminRatingCriteriaController {

    private final AdminRatingCriteriaService adminRatingCriteriaService;

    @Operation(summary = "별점기준 목록 조회")
    @GetMapping
    public ApiResponse<List<RatingCriteriaResponse>> getCriteriaList() {
        return ApiResponse.ok(adminRatingCriteriaService.getCriteriaList());
    }

    @Operation(summary = "별점기준 등록")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponse<Long> createCriteria(@Valid @RequestBody RatingCriteriaCreateRequest request) {
        return ApiResponse.of(HttpStatus.CREATED, adminRatingCriteriaService.createCriteria(request));
    }

    @Operation(summary = "별점기준 수정/비활성화")
    @PutMapping("/{criteriaId}")
    public ApiResponse<Void> updateCriteria(
            @PathVariable Long criteriaId,
            @RequestBody RatingCriteriaUpdateRequest request
    ) {
        adminRatingCriteriaService.updateCriteria(criteriaId, request);
        return ApiResponse.ok(null);
    }
}
