package com.readerprint.backend.book.service;

import com.readerprint.backend.book.client.LibraryApiClient;
import com.readerprint.backend.book.dto.response.BookItemResponse;
import com.readerprint.backend.book.dto.response.BookSearchResponse;
import com.readerprint.backend.book.dto.response.NlApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final LibraryApiClient libraryApiClient;

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

}
