package com.example.trover.domain.user.service;

import com.example.trover.domain.user.dto.LoginRequest;
import com.example.trover.domain.user.dto.LoginResponse;

import javax.validation.Valid;

public interface UserService {
    LoginResponse loginUser(@Valid LoginRequest loginRequest);
}
