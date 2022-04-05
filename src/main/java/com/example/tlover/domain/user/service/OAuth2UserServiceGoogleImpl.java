package com.example.tlover.domain.user.service;

import com.example.tlover.domain.user.constant.UserConstants;
import com.example.tlover.domain.user.dto.GoogleLoginRequest;
import com.example.tlover.domain.user.dto.LoginResponse;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user.repository.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuth2UserServiceGoogleImpl implements OAuth2UserServiceGoogle {

    private final UserRepository userRepository;

    private User saveOrUpdateGoogleUser(HashMap<String, Object> googleUserInfo) {
        User user = userRepository.findByUserEmailAndUserSocialProvider(googleUserInfo.get("email").toString(), UserConstants.ESocialProvider.eGoogle)
                .map(entity -> entity.updateNaverUser(googleUserInfo.get("name").toString(),
                        googleUserInfo.get("picture").toString()))
                .orElse(User.toEntityOfNaverUser(googleUserInfo));
        return userRepository.save(user);
    }

    @Override
    public LoginResponse validateGoogleAccessToken(GoogleLoginRequest googleLoginRequest) {

        HashMap<String, Object> googleUserInfo = getGoogleUserInfo(googleLoginRequest);

        return LoginResponse.from(saveOrUpdateGoogleUser(googleUserInfo));

    }

    private HashMap<String, Object> getGoogleUserInfo(GoogleLoginRequest googleLoginRequest) {

        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.setErrorHandler();
        String jwtToken = googleLoginRequest.getAccessToken();
        String requestUri = UriComponentsBuilder.fromHttpUrl("https://oauth2.googleapis.com/tokeninfo").queryParam("id_token",
                jwtToken).toUriString();

        HashMap<String, Object> userInfo = new HashMap<>();

        try {
            JsonObject resultJson = restTemplate.getForObject(requestUri, JsonObject.class);

            userInfo.put("name", resultJson.get("name").getAsString());
            userInfo.put("name", resultJson.get("email").getAsString());
            userInfo.put("name", resultJson.get("picture").getAsString());

        } catch (Exception e) {
            throw new RuntimeException("잘못된 접근입니다.");
        }
        return userInfo;
    }
}
