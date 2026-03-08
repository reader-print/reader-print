package com.readerprint.backend.inquiry.dto;

import jakarta.validation.constraints.NotBlank;

public record InquiryCreateRequest(
        @NotBlank String title,
        @NotBlank String content,
        @NotBlank String type
) {}
