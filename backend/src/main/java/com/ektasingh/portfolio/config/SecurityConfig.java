package com.ektasingh.portfolio.config;

import com.ektasingh.portfolio.auth.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
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

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    // private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth

                        // Authentication
                        .requestMatchers("/api/v1/auth/**","/swagger-ui/**","/v3/api-docs/**","/swagger-ui.html","/uploads/**","/api/v1/search/**").permitAll()

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

}