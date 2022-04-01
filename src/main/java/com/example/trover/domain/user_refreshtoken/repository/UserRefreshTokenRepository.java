package com.example.trover.domain.user_refreshtoken.repository;
import com.example.trover.domain.user_refreshtoken.entity.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken , Long> {

    Option<String> findUserRefreshTokenBy(String refreshTokenIdx);
}
