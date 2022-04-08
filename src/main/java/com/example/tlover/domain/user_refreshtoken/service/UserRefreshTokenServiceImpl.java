package com.example.tlover.domain.user_refreshtoken.service;

import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user_refreshtoken.entity.UserRefreshToken;
import com.example.tlover.domain.user_refreshtoken.repository.UserRefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRefreshTokenServiceImpl implements UserRefreshTokenService{


    private final UserRefreshTokenRepository userRefreshTokenRepository;

    @Override
    @Transactional
    public long insertRefreshToken(String refreshJwt, User user) {
//        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserUserId(user.getUserId());
//
//        if (userRefreshToken.getUserRefreshtokenToken() == null) {
//            //처음 로그인을 할 경우 INSERT
//            UserRefreshToken userRefreshTokeninsert = new UserRefreshToken();
//            userRefreshTokeninsert.setUser(user);
//            userRefreshTokeninsert.setUserRefreshtokenToken(refreshJwt);
//            userRefreshTokenRepository.save(userRefreshTokeninsert);
//            return userRefreshTokeninsert.getUserRefreshtokenId();
//        } else if (userRefreshToken.getUserRefreshtokenToken() != null) {
//            userRefreshToken.setUserRefreshtokenToken(refreshJwt);
////            userRefreshTokenRepository.save(userRefreshToken);
//        }


        UserRefreshToken userRefreshToken = new UserRefreshToken();
        userRefreshToken.setUser(user);
        userRefreshToken.setUserRefreshtokenToken(refreshJwt);
        userRefreshTokenRepository.save(userRefreshToken);
        return userRefreshToken.getUserRefreshtokenId();
//        return userRefreshToken.getUserRefreshtokenId();
    }
}
