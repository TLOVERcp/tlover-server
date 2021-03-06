package com.example.tlover.global.jwt.service;

import com.example.tlover.domain.user.entity.UserRefreshToken;
import com.example.tlover.global.jwt.secret.SecretKey;
import com.example.tlover.domain.user.repository.UserRefreshTokenRepository;
import com.example.tlover.global.jwt.exception.ExpireAccessException;
import com.example.tlover.global.jwt.exception.ExpireRefreshException;
import com.example.tlover.global.jwt.exception.NotFoundJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Getter
@Slf4j
public class JwtServiceImpl implements JwtService {

    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final long ACCESS_TOKEN_VALID_TIME = 60 * 60 * 2 * 1000L;   // 2시간
    private final long REFRESH_TOKEN_VALID_TIME  = 60 * 60 * 24 * 7 * 1000L;   // 1 달
    private Long userId;
    @Value("${spring.jwt.access-key}")
    public static String JWT_ACCESS_SECRET_KEY;
    @Value("${spring.jwt.refresh-key}")
    public static String JWT_REFRESH_SECRET_KEY;

    /**
     * 엑세스 토큰 생성
     * @param loginId
     * @return
     * @author 한규범
     */

    @Override
    public String createAccessJwt(String loginId) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type", "jwt")
                .claim("loginId", loginId)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME))
                .signWith(SignatureAlgorithm.HS256, SecretKey.JWT_ACCESS_SECRET_KEY)
                .compact();
    }

    /**
     * 리프레시 토큰 생성
     *
     * @param loginId
     * @return
     * @author 한규범
     */

    @Override
    public String createRefreshJwt(String loginId) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type", "jwt")
                .claim("loginId", loginId)
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
    public Long resolveRefreshToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return Long.valueOf(request.getHeader("X-REFRESH-TOKEN"));
    }

    /**
     * JWT 유효성 검사 및 UserId 리턴
     *
     * @return String
     * @author 한규범
     */
    @Override
    public String getLoginId() {
        //JWT 추출합니다.
        String accessToken = resolveAccessToken();
        if (accessToken == null || accessToken.length() == 0)
            throw new NotFoundJwtException();

        Long userRefreshTokenId = resolveRefreshToken();
        if (userRefreshTokenId == null) {
            throw new NotFoundJwtException();
        }

        Jws<Claims> claims;

        // JWT ACCESS parsing합니다.
        try{
            claims = Jwts.parser()
                    .setSigningKey(SecretKey.JWT_ACCESS_SECRET_KEY)
                    .parseClaimsJws(accessToken); // 파싱 및 검증, 실패 시 에러
        }catch (Exception e3){
            throw new ExpireAccessException();
        }

        // JWT REFRESH parsing합니다.
        try{
            //리프레시토큰 DB에서 가져오기
            UserRefreshToken userRefreshTokenRepo = userRefreshTokenRepository.findByUserRefreshtokenId(userRefreshTokenId);
            String userRefreshToken = userRefreshTokenRepo.getUserRefreshtokenToken();
            userId = userRefreshTokenRepo.getUser().getUserId();
            log.info("userId: "+userId);
            claims = Jwts.parser()
                    .setSigningKey(SecretKey.JWT_REFRESH_SECRET_KEY)
                    .parseClaimsJws(userRefreshToken);  // 파싱 및 검증, 실패 시 에러
        }catch (Exception e2){
            throw new ExpireRefreshException();
        }
        return claims.getBody().get("loginId",String.class);
    }
}