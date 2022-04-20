package com.example.tlover.domain.user_refreshtoken.repository;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user_refreshtoken.entity.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken , Long> {
    UserRefreshToken findByUserRefreshtokenId(Long userRefreshtokenId);

    UserRefreshToken findByUserUserId(Long userId);

//    UserRefreshToken findByUserRefreshtokenId(Long userRefreshtokenId)
}