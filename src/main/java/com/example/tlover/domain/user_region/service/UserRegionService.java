package com.example.tlover.domain.user_region.service;

import com.example.tlover.domain.user.dto.SignupRequest;
import com.example.tlover.domain.user_region.dto.UpdateUserRegionRequest;
import com.example.tlover.domain.user_region.dto.UpdateUserRegionResponse;
import com.example.tlover.domain.user_region.dto.UserRegionResponse;

import javax.validation.Valid;
import java.util.List;

public interface UserRegionService {
    void createUserRegion(SignupRequest signupRequest, Long loginId);
    List<UserRegionResponse> getUserRegion(String loginId);
    UpdateUserRegionResponse updateUserRegion(@Valid UpdateUserRegionRequest updateUserRegionRequest, String loginId);
}
