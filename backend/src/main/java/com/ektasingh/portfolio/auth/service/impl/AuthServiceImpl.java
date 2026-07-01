package com.ektasingh.portfolio.auth.service.impl;

import com.ektasingh.portfolio.auth.dto.request.LoginRequest;
import com.ektasingh.portfolio.auth.dto.response.LoginResponse;
import com.ektasingh.portfolio.auth.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import com.ektasingh.portfolio.auth.repository.UserRepository;
import com.ektasingh.portfolio.auth.service.AuthService;
import com.ektasingh.portfolio.auth.service.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import com.ektasingh.portfolio.auth.service.JwtService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        // String token = jwtService.generateToken(user);

        UserDetails userDetails =
                customUserDetailsService.loadUserByUsername(user.getUsername());

        String token = jwtService.generateToken(userDetails);

        return new LoginResponse(
                token,
                user.getUsername(),
                user.getRole().name()
        );
    }
}