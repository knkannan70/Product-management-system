package com.example.productmanagement.service.impl;

import com.example.productmanagement.dto.AuthResponse;
import com.example.productmanagement.dto.LoginRequest;
import com.example.productmanagement.dto.RegisterRequest;
import com.example.productmanagement.entity.Role;
import com.example.productmanagement.entity.User;
import com.example.productmanagement.exception.BadRequestException;
import com.example.productmanagement.repository.UserRepository;
import com.example.productmanagement.security.JwtTokenProvider;
import com.example.productmanagement.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public String register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new BadRequestException("Email is already registered!");
        }

        User user = User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER) // Default role
                .build();

        userRepository.save(user);

        return "User registered successfully!";
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return new AuthResponse(token);
    }
}
