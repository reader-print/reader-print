package com.readerprint.backend.review.repository;

import com.readerprint.backend.book.entity.Book;
import com.readerprint.backend.review.entity.Review;
import com.readerprint.backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {
    boolean existsByUserAndBook(User user, Book book);
}
