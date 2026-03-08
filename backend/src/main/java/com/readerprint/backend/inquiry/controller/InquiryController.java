package com.readerprint.backend.inquiry.controller;

import com.readerprint.backend.common.response.ApiResponse;
import com.readerprint.backend.inquiry.dto.InquiryCreateRequest;
import com.readerprint.backend.inquiry.dto.InquiryResponse;
import com.readerprint.backend.inquiry.service.InquiryService;
import com.readerprint.backend.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "사용자 문의")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/my/inquiries")
public class InquiryController {

    private final InquiryService inquiryService;

    @Operation(summary = "문의 등록")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponse<Long> createInquiry(
            @Valid @RequestBody InquiryCreateRequest request,
            @AuthenticationPrincipal User user
    ) {
        return ApiResponse.of(HttpStatus.CREATED, inquiryService.createInquiry(request, user));
    }

    @Operation(summary = "문의 조회")
    @GetMapping
    public ApiResponse<List<InquiryResponse>> getMyInquiries(@AuthenticationPrincipal User user) {
        return ApiResponse.ok(inquiryService.getMyInquiries(user));
    }
}
