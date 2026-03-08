package com.readerprint.backend.user.controller;

import com.readerprint.backend.common.response.ApiResponse;
import com.readerprint.backend.user.dto.ProfileResponse;
import com.readerprint.backend.user.entity.User;
import com.readerprint.backend.user.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "사용자 프로필")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/my/profile")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @Operation(summary = "프로필 조회")
    @GetMapping
    public ApiResponse<ProfileResponse> getProfile(@AuthenticationPrincipal User user) {
        return ApiResponse.ok(userProfileService.getProfile(user));
    }
}
