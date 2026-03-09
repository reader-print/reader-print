package com.readerprint.backend.inquiry.service;

import com.readerprint.backend.common.error.ErrorCode;
import com.readerprint.backend.common.error.exception.BadRequestException;
import com.readerprint.backend.inquiry.dto.AdminInquiryResponse;
import com.readerprint.backend.inquiry.entity.Inquiry;
import com.readerprint.backend.inquiry.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminInquiryService {

    private final InquiryRepository inquiryRepository;

    public Page<AdminInquiryResponse> getInquiries(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return inquiryRepository.findAll(pageable).map(AdminInquiryResponse::from);
    }

    @Transactional
    public void answerInquiry(Long inquiryId, String answer) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.INQUIRY_NOT_FOUND));
        inquiry.answer(answer);
    }
}
