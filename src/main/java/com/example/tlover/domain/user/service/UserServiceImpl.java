package com.example.tlover.domain.user.service;


import com.example.tlover.domain.user.dto.*;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user.exception.*;
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
    public User insertUser(SignupRequest signupRequest) {
        userRepository.save(signupRequest.toEntity());
        return signupRequest.toEntity();
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
        if (!user.getUserPassword().equals(resetPasswordRequest.getBeforePassword())) throw new NotEqualPasswordException();

        // 변경할 비밀번호가 기존 비밀번호와 일치할 때
        if (user.getUserPassword().equals(resetPasswordRequest.getAfterPassword())) throw new PasswordEqualException();

        user.setUserPassword(resetPasswordRequest.getAfterPassword());
        return user;
    }


}
