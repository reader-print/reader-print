package com.readerprint.backend.inquiry.dto;

import com.readerprint.backend.inquiry.entity.Inquiry;

import java.time.LocalDateTime;

public record AdminInquiryResponse(
        Long inquiryId,
        String userId,
        String title,
        String type,
        String status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static AdminInquiryResponse from(Inquiry inquiry) {
        return new AdminInquiryResponse(
                inquiry.getId(),
                inquiry.getUser().getUserId(),
                inquiry.getTitle(),
                inquiry.getType(),
                inquiry.getStatus().getValue(),
                inquiry.getCreatedAt(),
                inquiry.getUpdatedAt()
        );
    }
}
