package com.example.tlover.domain.user.service;

import com.example.tlover.domain.user.dto.*;
import com.example.tlover.domain.user.entity.User;

import javax.validation.Valid;

public interface UserService {
    User loginUser(@Valid LoginRequest loginRequest);

    User insertUser(@Valid SignupRequest signupRequest);

    void duplicateCheck(@Valid DuplicateRequest duplicateRequest);

    User getUserProfile(String loginId);

    User findUserId(@Valid FindIdRequest findIdRequest);

    User resetPassword(@Valid ResetPasswordRequest resetPasswordRequest, String loginId);

}
