package com.example.trover.domain.user.repository;

import com.example.trover.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserLoginId(String userLoginId);
}
