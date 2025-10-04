package com.readerprint.backend.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {


    @Bean
    public OpenAPI openAPI(){

        String securitySchemeName = "Bearer Authentication";

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList(securitySchemeName);

        return new OpenAPI()
                .info(new Info()
                        .title("Reader-Print Swagger")
                        .description("Reader-Print REST-API")
                        .version("1.0.0")
                )
                .addSecurityItem(securityRequirement)
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("JWT 토큰을 입력하세요 (Bearer 제외")));

    }

    private Info apiInfo(){
        return new Info()
                .title("Reader-Print Swagger")
                .description("Reader-Print REST-API")
                .version("1.0.0");
    }
}
