package com.readerprint.backend.rating.repository;

import com.readerprint.backend.rating.entity.UserRatingCriteria;
import com.readerprint.backend.rating.entity.UserRatingCriteriaId;
import com.readerprint.backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRatingCriteriaRepository extends JpaRepository<UserRatingCriteria, UserRatingCriteriaId> {
    List<UserRatingCriteria> findByUser(User user);
    void deleteByUser(User user);
}
