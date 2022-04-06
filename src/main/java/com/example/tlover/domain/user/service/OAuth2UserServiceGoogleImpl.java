package com.example.tlover.domain.user.service;

import com.example.tlover.domain.user.constant.UserConstants;
import com.example.tlover.domain.user.dto.GoogleLoginRequest;
import com.example.tlover.domain.user.dto.LoginResponse;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user.exception.oauth2.GoogleFailException;
import com.example.tlover.domain.user.repository.UserRepository;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;

import static com.example.tlover.domain.user.constant.UserConstants.EOAuth2UserServiceImpl.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuth2UserServiceGoogleImpl implements OAuth2UserServiceGoogle {

    private final UserRepository userRepository;

    @Value("${spring.security.oauth2.client.provider.google.user-info-uri}")
    private String googleUserInfoUrl;

    private User saveOrUpdateGoogleUser(HashMap<String, Object> googleUserInfo) {
        User user = userRepository.findByUserEmailAndUserSocialProvider(googleUserInfo.get(eEmailAttribute.getValue()).toString(), UserConstants.ESocialProvider.eGoogle)
                .map(entity -> entity.updateNaverUser(googleUserInfo.get(eNameAttribute.getValue()).toString(),
                        googleUserInfo.get(eGoogleProfileImageAttribute.getValue()).toString()))
                .orElse(User.toEntityOfGoogleUser(googleUserInfo));
        return userRepository.save(user);
    }

    /**
     *
     * @param googleLoginRequest Id_token을 전달해줍니다. ( != AccessToken )
     * getGoogleUserInfo를 통해서  구글 유저 정보를 Map에 담아서 리턴해줍니다.
     * saveOrUpdateGoogleUser에 구글 유저 정보가 담긴 Map객체(getGoogleUserInfo를)를 전달해줍니다
     * @return 로그인이 성공했다는 메시지를 가진 객체 (LoginResponse)를 리턴해준다.
     */

    @Override
    public LoginResponse validateGoogleAccessToken(GoogleLoginRequest googleLoginRequest) {

        HashMap<String, Object> googleUserInfo = getGoogleUserInfo(googleLoginRequest);

        return LoginResponse.from(saveOrUpdateGoogleUser(googleUserInfo));

    }

    private HashMap<String, Object> getGoogleUserInfo(GoogleLoginRequest googleLoginRequest) {

        RestTemplate restTemplate = new RestTemplate();

        String jwtToken = googleLoginRequest.getIdToken();
        String requestUri = UriComponentsBuilder.fromHttpUrl(googleUserInfoUrl).queryParam(eGoogleIdToken.getValue(),
                jwtToken).toUriString();

        HashMap<String, Object> userInfo = new HashMap<>();

        try {
            JsonObject resultJson = restTemplate.getForObject(requestUri, JsonObject.class);

            userInfo.put(eNameAttribute.getValue(), resultJson.get(eNameAttribute.getValue()).getAsString());
            userInfo.put(eEmailAttribute.getValue(), resultJson.get(eEmailAttribute.getValue()).getAsString());
            userInfo.put(eGoogleProfileImageAttribute.getValue(), resultJson.get(eGoogleProfileImageAttribute.getValue()).getAsString());

        } catch (Exception e) {
            throw new GoogleFailException(HttpStatus.BAD_REQUEST ,eGoogleTokenInvalid.getValue());
        }
        return userInfo;
    }
}
