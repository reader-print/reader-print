package com.readerprint.backend.book.client;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.readerprint.backend.book.dto.response.NlApiResponse;
import com.readerprint.backend.common.config.NlApiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class LibraryApiClient {

    private final WebClient webClient;
    private final NlApiProperties nlApiProperties;
    private final XmlMapper xmlMapper = new XmlMapper();

    public NlApiResponse searchBooks(String keyword, int page, int size) {
        String xml = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("key", nlApiProperties.getKey())
                        .queryParam("kwd", keyword)
                        .queryParam("pageNum", page)
                        .queryParam("pageSize", size)
                        .queryParam("target", "title")
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        try {
            return xmlMapper.readValue(xml, NlApiResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("국립도서관 API 응답 파싱 실패", e);
        }
    }
}
