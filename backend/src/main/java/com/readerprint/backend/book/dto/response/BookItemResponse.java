package com.readerprint.backend.book.dto.response;

public record BookItemResponse(
        String title,         // title_info
        String author,        // author_info
        String publisher,     // pub_info
        String publisherYear, // pub_year_info
        String isbn,          // isbn
        String category,      // category
        String type,          // type_name
        boolean hasOriginal,  // doc_yn
        String originalLink,  // org_link
        String detailLink,    // detail_link
        String controlNo      // control_no
) {
}
