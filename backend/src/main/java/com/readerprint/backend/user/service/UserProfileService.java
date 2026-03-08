package com.readerprint.backend.user.service;

import com.readerprint.backend.common.error.ErrorCode;
import com.readerprint.backend.common.error.exception.BadRequestException;
import com.readerprint.backend.tag.repository.UserTagRepository;
import com.readerprint.backend.user.dto.ProfileResponse;
import com.readerprint.backend.user.entity.User;
import com.readerprint.backend.user.entity.UserProfile;
import com.readerprint.backend.user.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserTagRepository userTagRepository;

    public ProfileResponse getProfile(User user) {
        UserProfile profile = userProfileRepository.findById(user.getSeq())
                .orElseThrow(() -> new BadRequestException(ErrorCode.USER_NOT_FOUND));

        List<ProfileResponse.TagDto> tags = userTagRepository.findByUser(user).stream()
                .map(ut -> new ProfileResponse.TagDto(
                        ut.getTag().getId(),
                        ut.getTag().getName(),
                        ut.getTag().getDescription()
                ))
                .toList();

        Boolean isSnsLinked = user.getUserDetail() != null && user.getUserDetail().getSnsLinked();

        return new ProfileResponse(
                profile.getProfileImage(),
                profile.getBio(),
                tags,
                isSnsLinked
        );
    }
}
