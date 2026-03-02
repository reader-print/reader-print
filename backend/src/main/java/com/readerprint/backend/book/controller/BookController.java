package com.readerprint.backend.book.controller;

import com.readerprint.backend.book.dto.request.BookRegisterRequest;
import com.readerprint.backend.book.dto.response.BookRegisterResponse;
import com.readerprint.backend.book.dto.response.BookSearchResponse;
import com.readerprint.backend.book.service.BookService;
import com.readerprint.backend.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;


    @Operation(summary = "책 목록 조회")
    @GetMapping("/books")
    public ApiResponse<BookSearchResponse> searchBooks(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.ok(bookService.searchBooks(keyword, page, size));
    }

    // 책 등록 (검색 결과에서 선택 후 DB 저장)
    @Operation(summary = "책 등록")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/books")
    public ApiResponse<BookRegisterResponse> registerBook(
            @Valid @RequestBody BookRegisterRequest request
    ) {
        return ApiResponse.of(HttpStatus.CREATED, bookService.registerBook(request));
    }
}
