package com.readerprint.backend.rating.service;

import com.readerprint.backend.common.error.ErrorCode;
import com.readerprint.backend.common.error.exception.BadRequestException;
import com.readerprint.backend.rating.dto.RatingCriteriaResponse;
import com.readerprint.backend.rating.entity.RatingCriteria;
import com.readerprint.backend.rating.entity.UserRatingCriteria;
import com.readerprint.backend.rating.entity.UserRatingCriteriaId;
import com.readerprint.backend.rating.repository.RatingCriteriaRepository;
import com.readerprint.backend.rating.repository.UserRatingCriteriaRepository;
import com.readerprint.backend.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRatingCriteriaService {

    private final UserRatingCriteriaRepository userRatingCriteriaRepository;
    private final RatingCriteriaRepository ratingCriteriaRepository;

    public List<RatingCriteriaResponse> getMyCriteria(User user) {
        return userRatingCriteriaRepository.findByUser(user).stream()
                .map(urc -> RatingCriteriaResponse.from(urc.getCriteria()))
                .toList();
    }

    @Transactional
    public void updateMyCriteria(User user, List<Long> criteriaIds) {
        userRatingCriteriaRepository.deleteByUser(user);

        List<UserRatingCriteria> newEntries = criteriaIds.stream()
                .map(criteriaId -> {
                    RatingCriteria criteria = ratingCriteriaRepository.findById(criteriaId)
                            .orElseThrow(() -> new BadRequestException(ErrorCode.CRITERIA_NOT_FOUND));
                    return UserRatingCriteria.builder()
                            .id(new UserRatingCriteriaId(user.getSeq(), criteriaId))
                            .user(user)
                            .criteria(criteria)
                            .build();
                })
                .toList();

        userRatingCriteriaRepository.saveAll(newEntries);
    }
}
