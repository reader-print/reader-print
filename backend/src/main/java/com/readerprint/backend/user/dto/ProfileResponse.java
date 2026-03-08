package com.readerprint.backend.user.dto;

import java.util.List;

public record ProfileResponse(
        String profileImage,
        String bio,
        List<TagDto> tags,
        Boolean isSnsLinked
) {
    public record TagDto(Long id, String name, String description) {}
}
