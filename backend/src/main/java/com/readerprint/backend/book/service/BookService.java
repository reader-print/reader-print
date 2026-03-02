package com.readerprint.backend.book.service;

import com.readerprint.backend.book.client.LibraryApiClient;
import com.readerprint.backend.book.dto.request.BookRegisterRequest;
import com.readerprint.backend.book.dto.response.BookItemResponse;
import com.readerprint.backend.book.dto.response.BookRegisterResponse;
import com.readerprint.backend.book.dto.response.BookSearchResponse;
import com.readerprint.backend.book.dto.response.NlApiResponse;
import com.readerprint.backend.book.entity.Book;
import com.readerprint.backend.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final LibraryApiClient libraryApiClient;
    private final BookRepository bookRepository;

    public BookSearchResponse searchBooks(String keyword, int page, int size) {
        NlApiResponse apiResponse = libraryApiClient.searchBooks(keyword, page, size);

        List<BookItemResponse> items = apiResponse.getItems().stream()
                .map(item -> new BookItemResponse(
                        item.getTitleInfo(),
                        item.getAuthorInfo(),
                        item.getPubInfo(),
                        item.getPubYearInfo(),
                        item.getIsbn(),
                        item.getCategory(),
                        item.getTypeName(),
                        item.getDocYn() != null && !"N".equals(item.getDocYn()),
                        item.getOrgLink(),
                        item.getDetailLink(),
                        item.getControlNo()
                ))
                .toList();

        return new BookSearchResponse(keyword, page, size, apiResponse.getTotal(), items);
    }

    @Transactional
    public BookRegisterResponse registerBook(BookRegisterRequest request) {
        // ISBN이 있으면 기존 책 반환 (중복 저장 방지)
        if (request.isbn() != null && !request.isbn().isBlank()) {
            Optional<Book> existing = bookRepository.findByIsbn(request.isbn());
            if (existing.isPresent()) {
                Book book = existing.get();
                return new BookRegisterResponse(book.getId(), book.getIsbn(), book.getTitle());
            }
        }

        Book book = Book.builder()
                .isbn(request.isbn())
                .title(request.title())
                .author(request.author())
                .publisher(request.publisher())
                .published(parseYear(request.publishedYear()))
                .genre(request.genre())
                .build();

        Book saved = bookRepository.save(book);
        return new BookRegisterResponse(saved.getId(), saved.getIsbn(), saved.getTitle());
    }

    private LocalDate parseYear(String publishedYear) {
        if (publishedYear == null || publishedYear.isBlank()) return null;
        try {
            return LocalDate.of(Integer.parseInt(publishedYear.trim()), 1, 1);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
