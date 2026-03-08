package com.readerprint.backend.inquiry.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InquiryStatus {
    WAITING("대기"),
    COMPLETED("답변완료");

    private final String value;
}
