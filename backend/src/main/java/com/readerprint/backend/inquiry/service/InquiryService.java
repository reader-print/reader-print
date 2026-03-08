package com.readerprint.backend.inquiry.service;

import com.readerprint.backend.inquiry.dto.InquiryCreateRequest;
import com.readerprint.backend.inquiry.dto.InquiryResponse;
import com.readerprint.backend.inquiry.entity.Inquiry;
import com.readerprint.backend.inquiry.repository.InquiryRepository;
import com.readerprint.backend.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryRepository inquiryRepository;

    @Transactional
    public Long createInquiry(InquiryCreateRequest request, User user) {
        Inquiry saved = inquiryRepository.save(Inquiry.builder()
                .user(user)
                .title(request.title())
                .content(request.content())
                .type(request.type())
                .build());
        return saved.getId();
    }

    public List<InquiryResponse> getMyInquiries(User user) {
        return inquiryRepository.findByUserOrderByCreatedAtDesc(user).stream()
                .map(InquiryResponse::from)
                .toList();
    }
}
