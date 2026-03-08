package com.readerprint.backend.inquiry.dto;

import com.readerprint.backend.inquiry.entity.Inquiry;

import java.time.LocalDateTime;

public record InquiryResponse(
        Long inquiryId,
        String title,
        String type,
        String status,
        String answer,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static InquiryResponse from(Inquiry inquiry) {
        return new InquiryResponse(
                inquiry.getId(),
                inquiry.getTitle(),
                inquiry.getType(),
                inquiry.getStatus().getValue(),
                inquiry.getAnswer(),
                inquiry.getCreatedAt(),
                inquiry.getUpdatedAt()
        );
    }
}
