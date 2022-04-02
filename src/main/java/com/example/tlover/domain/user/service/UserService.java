package com.example.tlover.domain.user.service;

import com.example.tlover.domain.user.dto.*;
import com.example.tlover.domain.user.entity.User;

import javax.validation.Valid;

public interface UserService {
    User loginUser(@Valid LoginRequest loginRequest);

    SignupResponse insertUser(@Valid SignupRequest signupRequest);

    DuplicateResponse duplicateCheck(@Valid DuplicateRequest duplicateRequest);

    ProfileResponse getUserProfile(String loginId);

    FindIdResponse findUserId(@Valid FindIdRequest findIdRequest);

    ResetPasswordResponse resetPassword(@Valid ResetPasswordRequest resetPasswordRequest);


}
