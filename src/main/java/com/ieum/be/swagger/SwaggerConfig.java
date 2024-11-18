package com.ieum.be.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new io.swagger.v3.oas.models.info.Info().title("ieum")
                .description("API description")
                .version("v0.0.1"));
    }
}