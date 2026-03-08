package com.readerprint.backend.rating.controller;

import com.readerprint.backend.common.response.ApiResponse;
import com.readerprint.backend.rating.dto.RatingCriteriaResponse;
import com.readerprint.backend.rating.dto.UserRatingCriteriaUpdateRequest;
import com.readerprint.backend.rating.service.UserRatingCriteriaService;
import com.readerprint.backend.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "사용자 별점기준")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/my/rating-criteria")
public class UserRatingCriteriaController {

    private final UserRatingCriteriaService userRatingCriteriaService;

    @Operation(summary = "내 별점기준 목록 조회")
    @GetMapping
    public ApiResponse<List<RatingCriteriaResponse>> getMyCriteria(@AuthenticationPrincipal User user) {
        return ApiResponse.ok(userRatingCriteriaService.getMyCriteria(user));
    }

    @Operation(summary = "내 별점기준 등록/수정")
    @PutMapping
    public ApiResponse<Void> updateMyCriteria(
            @RequestBody UserRatingCriteriaUpdateRequest request,
            @AuthenticationPrincipal User user
    ) {
        userRatingCriteriaService.updateMyCriteria(user, request.criteriaIds());
        return ApiResponse.ok(null);
    }
}
