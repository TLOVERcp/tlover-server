package com.example.tlover.domain.user_region.service;

import com.example.tlover.domain.region.entity.Region;
import com.example.tlover.domain.region.repository.RegionRepository;
import com.example.tlover.domain.user.dto.SignupRequest;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user.repository.UserRepository;
import com.example.tlover.domain.user_region.dto.UpdateUserRegionRequest;
import com.example.tlover.domain.user_region.dto.UpdateUserRegionResponse;
import com.example.tlover.domain.user_region.dto.UserRegionResponse;
import com.example.tlover.domain.user_region.entity.UserRegion;
import com.example.tlover.domain.user_region.repository.UserRegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserRegionServiceImpl implements UserRegionService{

    private final UserRegionRepository userRegionRepository;
    private final UserRepository userRepository;
    private final RegionRepository regionRepository;

    @Override
    public void createUserRegion(SignupRequest signupRequest, Long loginId) {
        String[] userRegions = signupRequest.getUserRegions();
        User user = userRepository.findById(loginId).get();
        for (String userRegionName : userRegions) {
            Region region = regionRepository.findByRegionName(userRegionName).get();
            userRegionRepository.save(UserRegion.toEntityOfUserRegion(user, region));
        }
    }

    @Override
    public List<UserRegionResponse> getUserRegion(String loginId) {
        User user = userRepository.findByUserLoginId(loginId).get();
        List<UserRegion> userRegions = userRegionRepository.findByUser_UserId(user.getUserId());
        List<UserRegionResponse> userRegionResponses = new ArrayList<>();

        for (UserRegion userRegion : userRegions) {
            userRegionResponses.add(UserRegionResponse.from(userRegion));
        }
        return userRegionResponses;
    }

    @Override
    public UpdateUserRegionResponse updateUserRegion(@Valid UpdateUserRegionRequest updateUserRegionRequest, String loginId) {
        User user = userRepository.findByUserLoginId(loginId).get();
        userRegionRepository.deleteAllByUser(user);
        UserRegion userRegion = new UserRegion();

        for (String userRegionName : updateUserRegionRequest.getUserRegions()) {
            Region region = regionRepository.findByRegionName(userRegionName).get();
            userRegion = UserRegion.toEntityOfUserRegion(user, region);
            userRegionRepository.save(userRegion);
        }

        return UpdateUserRegionResponse.from(userRegion);
    }

}
