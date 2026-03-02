package com.readerprint.backend.book.dto.response;

public record BookRegisterResponse(
        Long bookId,
        String isbn,
        String title
) {
}
