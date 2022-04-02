package com.example.tlover.domain.user.service;


import com.example.tlover.domain.user.dto.*;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user.exception.InvalidPasswordException;
import com.example.tlover.domain.user.exception.NotFoundUserException;
import com.example.tlover.domain.user.exception.UserIdDuplicateException;
import com.example.tlover.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public User loginUser(LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByUserLoginId(loginRequest.getLoginId());

        if (user.isEmpty()) throw new NotFoundUserException();
        if (!user.get().getUserPassword().equals(loginRequest.getPassword())) throw new InvalidPasswordException();
        return user.get();
    }

    @Override
    public SignupResponse insertUser(SignupRequest signupRequest) {
        userRepository.save(signupRequest.toEntity());
        return SignupResponse.builder()
                .message(signupRequest.getUserNickName() + "님, 회원가입이 완료되었습니다.")
                .build();
    }

    @Override
    public DuplicateResponse duplicateCheck(DuplicateRequest duplicateRequest) {
        Optional<User> user = userRepository.findByUserLoginId(duplicateRequest.getLoginId());

        if (!user.isEmpty()) throw new UserIdDuplicateException();

        return DuplicateResponse.builder()
                .message("사용가능한 아이디입니다.")
                .build();
    }

    @Override
    public ProfileResponse getUserProfile(String loginId) {
        Optional<User> user = userRepository.findByUserLoginId(loginId);
        return null;
    }

    @Override
    public FindIdResponse findUserId(FindIdRequest findIdRequest) {
        Optional<User> user = userRepository.findByUserLoginId(findIdRequest.getUserPhoneNum());
        return FindIdResponse.builder()
                .userId(user.get().getUserId())
                .build();
    }

    @Override
    @Transactional
    public ResetPasswordResponse resetPassword(ResetPasswordRequest resetPasswordRequest) {
        Optional<User> user = userRepository.findByUserLoginId(resetPasswordRequest.getBeforePassword());
        user.get().setUserPassword(resetPasswordRequest.getAfterPassword());
        return null;
    }


}
