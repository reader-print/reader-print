package com.readerprint.backend.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    private final NlApiProperties nlApiProperties;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(nlApiProperties.getBaseUrl())
                .build();
    }
}
