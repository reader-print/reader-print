package com.readerprint.backend.book.controller;

import com.readerprint.backend.book.dto.response.BookSearchResponse;
import com.readerprint.backend.book.service.BookService;
import com.readerprint.backend.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "책")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;


    @Operation(summary = "책 목록 조회(책 검색)")
    @GetMapping("/books")
    public ApiResponse<BookSearchResponse> searchBooks(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.ok(bookService.searchBooks(keyword, page, size));
    }

}
