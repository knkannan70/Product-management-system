package com.example.productmanagement.service;

import com.example.productmanagement.dto.AuthResponse;
import com.example.productmanagement.dto.LoginRequest;
import com.example.productmanagement.dto.RegisterRequest;

public interface AuthService {
    String register(RegisterRequest registerRequest);
    AuthResponse login(LoginRequest loginRequest);
}
