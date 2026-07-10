package com.ektasingh.portfolio.auth.service;

import com.ektasingh.portfolio.auth.dto.request.LoginRequest;
import com.ektasingh.portfolio.auth.dto.response.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);

}