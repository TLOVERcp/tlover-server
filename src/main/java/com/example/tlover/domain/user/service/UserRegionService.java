package com.example.tlover.domain.user.service;

import com.example.tlover.domain.user.dto.SignupRequest;
import com.example.tlover.domain.user.dto.UpdateUserRegionRequest;
import com.example.tlover.domain.user.dto.UserRegionResponse;

import javax.validation.Valid;
import java.util.List;

public interface UserRegionService {
    void createUserRegion(SignupRequest signupRequest, Long loginId);
    List<UserRegionResponse> getUserRegion(String loginId);
    void updateUserRegion(@Valid UpdateUserRegionRequest updateUserRegionRequest, String loginId);
    void checkRegionName(String[] regionNameList);
}
