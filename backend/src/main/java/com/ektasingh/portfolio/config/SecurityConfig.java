package com.ektasingh.portfolio.config;

import com.ektasingh.portfolio.auth.filter.JwtAuthenticationFilter;
import com.ektasingh.portfolio.common.exception.RestAccessDeniedHandler;
import com.ektasingh.portfolio.common.exception.RestAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final RestAccessDeniedHandler restAccessDeniedHandler;
    // private final AuthenticationProvider authenticationProvider;

    @Value("${app.cors.allowed-origins}")
    private String allowedOrigins;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(restAuthenticationEntryPoint)
                        .accessDeniedHandler(restAccessDeniedHandler))

                .authorizeHttpRequests(auth -> auth

                        // Authentication
                        .requestMatchers("/api/v1/auth/**","/swagger-ui/**","/v3/api-docs/**","/swagger-ui.html","/api/v1/search/**").permitAll()

                        // Public Portfolio APIs
                        .requestMatchers(HttpMethod.GET, "/api/v1/profile/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/contact/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/education/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/experience/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/project/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/publication/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/certificate/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/achievement/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/technology/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/blog/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/skill/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/portfolio/**").permitAll()

                        // Everything else requires login
                        .anyRequest().authenticated())

                // .authenticationProvider(authenticationProvider)

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of(allowedOrigins.split(",")));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}