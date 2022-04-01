package com.example.tlover.domain.user.service;

import com.example.tlover.domain.user.dto.LoginRequest;
import com.example.tlover.domain.user.dto.LoginResponse;

import javax.validation.Valid;

public interface UserService {
    LoginResponse loginUser(@Valid LoginRequest loginRequest);
}
