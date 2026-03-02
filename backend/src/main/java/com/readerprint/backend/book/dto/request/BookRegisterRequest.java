package com.readerprint.backend.book.dto.request;

import jakarta.validation.constraints.NotBlank;

public record BookRegisterRequest(
        String isbn,
        @NotBlank String title,
        String author,
        String publisher,
        String publishedYear,
        String genre
) {
}
