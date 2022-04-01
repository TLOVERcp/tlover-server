package com.example.trover.domain.user.service;

import com.example.trover.domain.user.dto.LoginRequest;
import com.example.trover.domain.user.dto.LoginResponse;
import com.example.trover.domain.user.entity.User;
import com.example.trover.domain.user.exception.InvalidPasswordException;
import com.example.trover.domain.user.exception.NotFoundUserException;
import com.example.trover.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public LoginResponse loginUser(LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByUserLoginId(loginRequest.getLoginId());
        System.out.println(loginRequest.getLoginId());
        System.out.println(user.get().getUserLoginId());
        if (user.isEmpty()) throw new NotFoundUserException("해당 LoginId의 User를 찾을 수 없습니다.");
        if (!user.get().getUserPassword().equals(loginRequest.getPassword())) throw new InvalidPasswordException("Password가 잘못되었습니다.");
        return LoginResponse.from(user.get());
    }
}
