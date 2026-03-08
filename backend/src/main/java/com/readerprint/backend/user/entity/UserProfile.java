package com.readerprint.backend.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_profile")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {

//    user_seq bigint [pk, not null]             // User.seq 참조
//    bio varchar(300)
//    profile_image varchar(255)
//    created_at timestamp
//    updated_at timestamp

    @Id
    @Column(name = "user_seq")
    private Long userSeq;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_seq")
    private User user;

    @Column(name = "bio")
    private String bio;

    @Column(name = "profile_image")
    private String profileImage;


    @Builder.Default
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;








}
