package com.example.tlover.domain.user.repository;
import com.example.tlover.domain.user.entity.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken , Long> {
    UserRefreshToken findByUserRefreshtokenId(Long userRefreshtokenId);

    UserRefreshToken findByUserUserId(Long userId);

}