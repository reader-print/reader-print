package com.readerprint.backend.tag.repository;

import com.readerprint.backend.tag.entity.UserTag;
import com.readerprint.backend.tag.entity.UserTagId;
import com.readerprint.backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTagRepository extends JpaRepository<UserTag, UserTagId> {
    List<UserTag> findByUser(User user);
}
