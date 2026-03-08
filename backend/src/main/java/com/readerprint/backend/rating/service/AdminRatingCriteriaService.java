package com.readerprint.backend.rating.service;

import com.readerprint.backend.common.error.ErrorCode;
import com.readerprint.backend.common.error.exception.BadRequestException;
import com.readerprint.backend.rating.dto.RatingCriteriaCreateRequest;
import com.readerprint.backend.rating.dto.RatingCriteriaResponse;
import com.readerprint.backend.rating.dto.RatingCriteriaUpdateRequest;
import com.readerprint.backend.rating.entity.RatingCriteria;
import com.readerprint.backend.rating.repository.RatingCriteriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminRatingCriteriaService {

    private final RatingCriteriaRepository ratingCriteriaRepository;

    public List<RatingCriteriaResponse> getCriteriaList() {
        return ratingCriteriaRepository.findAll().stream()
                .map(RatingCriteriaResponse::from)
                .toList();
    }

    @Transactional
    public Long createCriteria(RatingCriteriaCreateRequest request) {
        if (ratingCriteriaRepository.findByName(request.name()).isPresent()) {
            throw new BadRequestException(ErrorCode.CRITERIA_DUPLICATE);
        }

        RatingCriteria saved = ratingCriteriaRepository.save(
                RatingCriteria.builder()
                        .name(request.name())
                        .description(request.description())
                        .build()
        );
        return saved.getId();
    }

    @Transactional
    public void updateCriteria(Long criteriaId, RatingCriteriaUpdateRequest request) {
        RatingCriteria criteria = ratingCriteriaRepository.findById(criteriaId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.CRITERIA_NOT_FOUND));

        if (request.name() != null && !request.name().equals(criteria.getName())) {
            ratingCriteriaRepository.findByName(request.name()).ifPresent(c -> {
                throw new BadRequestException(ErrorCode.CRITERIA_DUPLICATE);
            });
        }

        criteria.update(request.name(), request.description(), request.isActivate());
    }
}
