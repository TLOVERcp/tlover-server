package com.example.tlover.domain.user.repository;

import com.example.tlover.domain.user.constant.UserConstants.ESocialProvider;
import com.example.tlover.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    Optional<User> findByUserId(Long userId);

    Optional<User> findByUserLoginId(String userLoginId);

    Optional<User> findByUserPhoneNum(String userPhoneNum);

    Optional<User> findByUserEmail(String userEmail);

    Optional<User> findByUserEmailAndUserSocialProvider(String toString, ESocialProvider eSocialProvider);

    Optional<User> findByUserNickName(String userNickName);
}


