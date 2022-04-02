package com.example.tlover.global.config.jwt.service;

import com.example.tlover.domain.user_refreshtoken.entity.UserRefreshToken;
import com.example.tlover.global.config.jwt.Secret.SecretKey;
import com.example.tlover.domain.user_refreshtoken.repository.UserRefreshTokenRepository;
import com.example.tlover.global.config.jwt.exception.ExpireJwtException;
import com.example.tlover.global.config.jwt.exception.NotFoundJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final UserRefreshTokenRepository userRefreshTokenRepository;

    private final long ACCESS_TOKEN_VALID_TIME = 60 * 60 * 2 * 1000L;   // 2시간
    private final long REFRESH_TOKEN_VALID_TIME = 60 * 60 * 24 * 7 * 1000L;   // 1 달

    /**
     * 액세스 토큰 생성
     *
     * @param userId
     * @author 한규범
     */
    @Override
    public String createAccessJwt(String userId) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type", "jwt")
                .claim("userId", userId)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME))
                .signWith(SignatureAlgorithm.HS256, SecretKey.JWT_ACCESS_SECRET_KEY)
                .compact();
    }

    /**
     * 리프레시 토큰 생성
     *
     * @param userId
     * @return
     * @author 한규범
     */
    @Override
    public String createRefreshJwt(String userId) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type", "jwt")
                .claim("userId", userId)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_VALID_TIME))
                .signWith(SignatureAlgorithm.HS256, SecretKey.JWT_REFRESH_SECRET_KEY)
                .compact();
    }

    /**
     * Header에서 X-ACCESS-TOKEN 으로 JWT 추출
     *
     * @return String
     * @author 한규범
     */
    @Override
    public String resolveAccessToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("X-ACCESS-TOKEN");
    }

    /**
     * Header에서 X-REFRESH-TOKEN 으로 JWT 추출
     *
     * @return String
     * @author 한규범
     */
    @Override
    public String resolveRefreshToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("X-REFRESH-TOKEN");
    }

    /**
     * Header에서 X-REFRESH-TOKEN 으로 JWT 추출
     *
     * @return String
     * @author 한규범
     */
    @Override
    public String getUserId() {
        //JWT 추출합니다.
        String accessToken = resolveAccessToken();
        if (accessToken == null || accessToken.length() == 0) {
            throw new NotFoundJwtException("ACCESS-TOKEN이 비어있습니다.");
        }

        // JWT parsing합니다.
        Jws<Claims> claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SecretKey.JWT_ACCESS_SECRET_KEY)
                    .parseClaimsJws(accessToken); // 파싱 및 검증, 실패 시 에러
        } catch (Exception ignored) {
            //엑세스 토큰 만료
            try {
                String refreshTokenIdx = resolveRefreshToken();
                if (refreshTokenIdx == null || refreshTokenIdx.length() == 0) {
                    throw new NotFoundJwtException("REFRESH-TOKEN이 비어있습니다.");
                } else {
                    //리프레시토큰 DB에서 가져오기
                    Optional<UserRefreshToken> userRefreshTokenRepo = userRefreshTokenRepository.findByUserRefreshtokenId(refreshTokenIdx);
                    String userRefreshToken = userRefreshTokenRepo.get().getUserRefreshtokenToken();

                    claims = Jwts.parser()
                            .setSigningKey(SecretKey.JWT_REFRESH_SECRET_KEY)
                            .parseClaimsJws(userRefreshToken);  // 파싱 및 검증, 실패 시 에러
                    throw new ExpireJwtException("ACCESS-TOKEN이 만료되었습니다.");
                }
            } catch (Exception ignored2) {
                //리프레시 토큰 만료될 경우
                throw new ExpireJwtException("REFRESH-TOKEN이 만료되었습니다.");
            }

        }
        return null;
    }
}