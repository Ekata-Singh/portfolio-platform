package com.ektasingh.portfolio.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
// import io.swagger.v3.oas.models.security.SecurityScheme.In;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
        // in = In.HEADER
)
public class SwaggerConfig {

    @Bean
    public OpenAPI portfolioOpenAPI() {

        return new OpenAPI()

                .info(new Info()

                        .title("Portfolio Platform API")

                        .description("REST API for Portfolio Platform built using Spring Boot and JWT Authentication.")

                        .version("v1.0")

                        .contact(new Contact()
                                .name("Ekta Singh")
                                .email("ekta@example.com"))

                        .license(new License()
                                .name("MIT License")))

                .addSecurityItem(new SecurityRequirement()
                        .addList("Bearer Authentication"));
    }
}