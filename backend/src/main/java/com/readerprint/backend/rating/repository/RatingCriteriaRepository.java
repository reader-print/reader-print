package com.readerprint.backend.rating.repository;

import com.readerprint.backend.rating.entity.RatingCriteria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RatingCriteriaRepository extends JpaRepository<RatingCriteria, Long> {
    Optional<RatingCriteria> findByName(String name);
}
