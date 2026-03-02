package com.readerprint.backend.review.entity;

import com.readerprint.backend.book.entity.Book;
import com.readerprint.backend.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "review")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq", nullable = false, foreignKey = @ForeignKey(name = "fk_review_user"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false, foreignKey = @ForeignKey(name = "fk_review_book"))
    private Book book;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Builder.Default
    @Column(name = "is_blocked", nullable = false)
    private boolean blocked = false;

    @Column(name = "blocked_reason", length = 255)
    private String blockedReason;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "reading_start_date")
    private LocalDate readingStartDate;

    @Column(name = "reading_end_date")
    private LocalDate readingEndDate;

    public void update(String content, LocalDate readingStartDate, LocalDate readingEndDate) {
        this.content = content;
        this.readingStartDate = readingStartDate;
        this.readingEndDate = readingEndDate;
    }
}
