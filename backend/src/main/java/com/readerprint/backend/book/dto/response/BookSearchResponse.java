package com.readerprint.backend.book.dto.response;

import java.util.List;

public record BookSearchResponse(
        String keyword,
        int page,
        int size,
        int totalCount,
        List<BookItemResponse> items
) {
}
