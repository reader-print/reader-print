package com.readerprint.backend.review.controller;

import com.readerprint.backend.common.response.ApiResponse;
import com.readerprint.backend.review.dto.ReviewCreateResponse;
import com.readerprint.backend.review.dto.ReviewRequest;
import com.readerprint.backend.review.dto.ReviewResponse;
import com.readerprint.backend.review.service.ReviewService;
import com.readerprint.backend.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "사용자 리뷰")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/my/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "리뷰 목록 조회")
    @GetMapping
    public ApiResponse<List<ReviewResponse>> getMyReviews(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @AuthenticationPrincipal User user
    ) {
        return ApiResponse.ok(reviewService.getMyReviews(startDate, endDate, user.getSeq()));
    }

    @Operation(summary = "리뷰 등록")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponse<ReviewCreateResponse> createReview(
            @Valid @RequestBody ReviewRequest request,
            @AuthenticationPrincipal User user
    ) {
        return ApiResponse.of(HttpStatus.CREATED, reviewService.createReview(request, user));
    }

    @Operation(summary = "리뷰 수정")
    @PutMapping("/{reviewId}")
    public ApiResponse<ReviewCreateResponse> updateReview(
            @PathVariable Long reviewId,
            @Valid @RequestBody ReviewRequest request,
            @AuthenticationPrincipal User user
    ) {
        return ApiResponse.ok(reviewService.updateReview(reviewId, request, user));
    }
}
