package com.example.productmanagement.service;

import com.example.productmanagement.dto.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Long id);
    void deleteUser(Long id);
}
