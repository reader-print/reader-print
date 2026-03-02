package com.readerprint.backend.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "nl.api")
@Getter
@Setter
public class NlApiProperties {
    private String baseUrl;
    private String key;
}
