package com.readerprint.backend.inquiry.repository;

import com.readerprint.backend.inquiry.entity.Inquiry;
import com.readerprint.backend.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    List<Inquiry> findByUserOrderByCreatedAtDesc(User user);
    Page<Inquiry> findAll(Pageable pageable);
}
