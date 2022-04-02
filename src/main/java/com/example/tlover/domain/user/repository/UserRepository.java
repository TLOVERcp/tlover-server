package com.example.tlover.domain.user.repository;

import com.example.tlover.domain.user.constant.UserConstants;
import com.example.tlover.domain.user.constant.UserConstants.ESocialProvider;
import com.example.tlover.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.stream.LongStream;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserLoginId(String userLoginId);
    Optional<User> findByUserEmailAndUserSocialProvider(String toString, ESocialProvider eSocialProvider);

}
