package com.ektasingh.portfolio.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.tags.Tag;
// import io.swagger.v3.oas.models.security.SecurityScheme.In;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

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
                        .addList("Bearer Authentication"))

                .tags(List.of(
                        new Tag().name("Authentication").description("Login & JWT Authentication APIs"),
                        new Tag().name("Profile").description("Profile Management APIs"),
                        new Tag().name("Projects").description("Project Management APIs"),
                        new Tag().name("Blogs").description("Blog Management APIs"),
                        new Tag().name("Skills").description("Skill Management APIs"),
                        new Tag().name("Technology").description("Technology Management APIs"),
                        new Tag().name("Certificates").description("Certification Management APIs"),
                        new Tag().name("Publications").description("Publication Management APIs"),
                        new Tag().name("Achievements").description("Achievement Management APIs"),
                        new Tag().name("Education").description("Education Management APIs"),
                        new Tag().name("Experience").description("Work Experience Management APIs"),
                        new Tag().name("Contact").description("Contact Management APIs"),
                        new Tag().name("Portfolio").description("Portfolio Aggregation APIs"),
                        new Tag().name("Search").description("Global Search APIs")));
    }
}