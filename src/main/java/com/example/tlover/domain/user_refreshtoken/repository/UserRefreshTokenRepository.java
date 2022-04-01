package com.example.tlover.domain.user_refreshtoken.repository;
import com.example.tlover.domain.user_refreshtoken.entity.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken , Long> {
}
