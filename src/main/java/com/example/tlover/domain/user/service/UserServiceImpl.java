package com.example.tlover.domain.user.service;


import com.example.tlover.domain.user.dto.*;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user.exception.*;
import com.example.tlover.domain.user.repository.UserRepository;
import com.example.tlover.global.encryption.SHA256Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final SHA256Util sha256Util;


    @Override
    public User loginUser(LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByUserLoginId(loginRequest.getLoginId());

        if (user.isEmpty()) throw new NotFoundUserException();
        if (!user.get().getUserPassword().equals(sha256Util.encrypt(loginRequest.getPassword()))) throw new InvalidPasswordException();
        return user.get();
    }

    @Override
    public User insertUser(SignupRequest signupRequest) {

        User user = signupRequest.toEntity(sha256Util.encrypt(signupRequest.getPassword()));
        this.duplicateCheck(DuplicateRequest.from(user));
        userRepository.save(user);
        return user;
    }

    @Override
    public void duplicateCheck(DuplicateRequest duplicateRequest) {
        Optional<User> user = userRepository.findByUserLoginId(duplicateRequest.getLoginId());

        if (!user.isEmpty()) throw new UserIdDuplicateException();
    }

    @Override
    public User getUserProfile(String loginId) {
        return userRepository.findByUserLoginId(loginId).get();
    }

    @Override
    public User findUserId(FindIdRequest findIdRequest) {
        Optional<User> user = userRepository.findByUserPhoneNum(findIdRequest.getUserPhoneNum());

        if (user.isEmpty()) throw new NotFoundUserException();
        return user.get();
    }

    @Override
    @Transactional
    public User resetPassword(ResetPasswordRequest resetPasswordRequest, String loginId) {
        User user = userRepository.findByUserLoginId(loginId).get();

        // 기존 비밀번호가 일치하지 않을 때
        if (!user.getUserPassword().equals(sha256Util.encrypt(resetPasswordRequest.getBeforePassword())))
            throw new NotEqualPasswordException();

        // 변경할 비밀번호가 기존 비밀번호와 일치할 때
        if (user.getUserPassword().equals(sha256Util.encrypt(resetPasswordRequest.getAfterPassword())))
            throw new PasswordEqualException();

        user.setUserPassword(sha256Util.encrypt(resetPasswordRequest.getAfterPassword()));
        return user;
    }

    @Override
    @Transactional
    public void updateUserProfile(UserProfileRequest userProfileRequest) {
        //User user = this.getUserProfile(userProfileRequest.getLoginId());
        //user.setUserLoginId(userProfileRequest.getLoginId());
        //user.setUserEmail(userProfileRequest.getUserEmail());
        //user.setUserNickName(userProfileRequest.getUserNickName());

        //user.setUserProfileImg(userProfileRequest);

    }

    @Override
    public void findPassword(FindPasswordRequest findPasswordRequest) {

    }

    @Override
    @Transactional
    public void withdrawUser(WithdrawUserRequest withdrawUserRequest) {
        User user = this.getUserProfile("dd");
        user.setUserState("deleted");
    }


}
