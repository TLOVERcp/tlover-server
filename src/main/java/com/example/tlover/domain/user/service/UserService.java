package com.example.tlover.domain.user.service;

import com.example.tlover.domain.user.dto.*;
import com.example.tlover.domain.user.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface UserService {
    User loginUser(@Valid LoginRequest loginRequest);

    User insertUser(@Valid SignupRequest signupRequest);

    void loginIdDuplicateCheck(String loginId);

    void phoneNumDuplicateCheck(String phoneNum);

    User getUserProfile(String loginId);

    FindIdResponse findUserId(@Valid FindIdRequest findIdRequest, CertifiedValue certifiedValue) throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException, JsonProcessingException;

    User resetPassword(@Valid ResetPasswordRequest resetPasswordRequest, String loginId);

    User updateUserProfile(String loginId, @Valid UserProfileRequest userProfileRequest, MultipartFile file);

    FindPasswordResponse findPassword(@Valid FindPasswordRequest findPasswordRequest) throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException, JsonProcessingException;

    void withdrawUser(@Valid WithdrawUserRequest withdrawUserRequest, String loginId);

}
