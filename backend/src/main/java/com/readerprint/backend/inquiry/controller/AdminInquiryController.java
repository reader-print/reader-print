package com.readerprint.backend.inquiry.controller;

import com.readerprint.backend.common.response.ApiResponse;
import com.readerprint.backend.inquiry.dto.AdminInquiryResponse;
import com.readerprint.backend.inquiry.dto.InquiryAnswerRequest;
import com.readerprint.backend.inquiry.service.AdminInquiryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Tag(name = "관리자 문의")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/inquiries")
public class AdminInquiryController {

    private final AdminInquiryService adminInquiryService;

    @Operation(summary = "문의 목록 조회")
    @GetMapping
    public ApiResponse<Page<AdminInquiryResponse>> getInquiries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ApiResponse.ok(adminInquiryService.getInquiries(page, size));
    }

    @Operation(summary = "문의 답변 등록")
    @PutMapping("/{inquiryId}/answer")
    public ApiResponse<Void> answerInquiry(
            @PathVariable Long inquiryId,
            @Valid @RequestBody InquiryAnswerRequest request
    ) {
        adminInquiryService.answerInquiry(inquiryId, request.answer());
        return ApiResponse.ok(null);
    }
}
