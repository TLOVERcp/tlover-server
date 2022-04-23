package com.example.tlover.domain.user_region.service;

import com.example.tlover.domain.region.entity.Region;
import com.example.tlover.domain.region.exception.NotFoundRegionNameException;
import com.example.tlover.domain.region.repository.RegionRepository;
import com.example.tlover.domain.user.dto.SignupRequest;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user.repository.UserRepository;
import com.example.tlover.domain.user_region.dto.UpdateUserRegionRequest;
import com.example.tlover.domain.user_region.dto.UserRegionResponse;
import com.example.tlover.domain.user_region.entity.UserRegion;
import com.example.tlover.domain.user_region.exception.NotFoundUserRegionException;
import com.example.tlover.domain.user_region.repository.UserRegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static com.example.tlover.domain.user_region.constant.UserRegionConstants.*;


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
    @Transactional
    public void updateUserRegion(@Valid UpdateUserRegionRequest updateUserRegionRequest, String loginId) {

        String[] userRegions = updateUserRegionRequest.getUserRegions();
        User user = userRepository.findByUserLoginId(loginId).get();
        userRegionRepository.deleteAllByUser(user);

        for (String userRegionName : userRegions) {
            Region region = regionRepository.findByRegionName(userRegionName)
                    .orElseThrow(()->new NotFoundUserRegionException(
                            EUserRegionExceptionMessage.eNotFoundUserRegionExceptionMessage.getValue()));
            userRegionRepository.save(UserRegion.toEntityOfUserRegion(user, region));
        }
    }

    /**
     * 지역 이름 확인
     * @param regionNameList
     * @return
     * @author 윤여찬
     */
    @Override
    public void checkRegionName(String[] regionNameList) {

        if (regionNameList.length != 0) {

            for (String regionName : regionNameList) {
                regionRepository.findByRegionName(regionName).orElseThrow(NotFoundRegionNameException::new);

            }
        }
    }
}
