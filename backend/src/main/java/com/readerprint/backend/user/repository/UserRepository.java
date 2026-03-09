package com.readerprint.backend.user.repository;

import com.readerprint.backend.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUserId(String userId);
    boolean existsByNickname(String nickname);

    Optional<User> findByUserId(String userId);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.userDetail WHERE u.userId = :userId")
    Optional<User> findByUserIdWithDetail(String userId);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.userDetail ORDER BY u.createdAt DESC")
    List<User> findAllWithDetail();

    @Query(value = "SELECT u FROM User u LEFT JOIN FETCH u.userDetail",
           countQuery = "SELECT COUNT(u) FROM User u")
    Page<User> findAllWithDetail(Pageable pageable);
}
