package com.readerprint.backend.review.controller;

import com.readerprint.backend.common.response.ApiResponse;
import com.readerprint.backend.review.dto.ReviewResponse;
import com.readerprint.backend.review.service.ReviewService;
import com.readerprint.backend.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Tag(name = "사용자 리뷰")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/my/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "사용자가 읽은 책 목록 조회")
    @GetMapping
    public ApiResponse<List<ReviewResponse>> getMyReviews(@RequestParam LocalDate startDate,
                                                          @RequestParam LocalDate endDate,
                                                          @AuthenticationPrincipal User user){
        Long userSeq = user.getSeq();
        List<ReviewResponse> response = reviewService.getMyReviews(startDate, endDate, userSeq);

        return ApiResponse.ok(response);
    }
}
