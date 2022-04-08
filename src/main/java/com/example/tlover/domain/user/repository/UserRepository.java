package com.example.tlover.domain.user.repository;

import com.example.tlover.domain.user.constant.UserConstants.ESocialProvider;
import com.example.tlover.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserLoginId(String userLoginId);

    Optional<User> findByUserPhoneNum(String userPhoneNum);

    Optional<User> findByUserEmailAndUserSocialProvider(String toString, ESocialProvider eSocialProvider);

    User findByUserNickName(String userNickName);

}
