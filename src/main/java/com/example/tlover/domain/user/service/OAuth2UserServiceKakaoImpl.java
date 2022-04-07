package com.example.tlover.domain.user.service;

import com.example.tlover.domain.user.constant.UserConstants;
import com.example.tlover.domain.user.dto.KakaoLoginRequest;
import com.example.tlover.domain.user.dto.LoginResponse;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user.exception.oauth2.KakaoFailException;
import com.example.tlover.domain.user.repository.UserRepository;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class OAuth2UserServiceKakaoImpl implements OAuth2UserServiceKakao {

    private final UserRepository userRepository;

    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String kakaoUserInfoUrl;

    /**
     * kakao login or sign up
     */

    @Override
    public LoginResponse validateKakaoAccessToken(KakaoLoginRequest kakaoLoginRequest) {
        HashMap<String, Object> kakaoUserInfo = getKakaoUserInfo(kakaoLoginRequest);
        return LoginResponse.from(saveOrUpdateKakaoUser(kakaoUserInfo));
    }

    private HashMap<String, Object> getKakaoUserInfo(KakaoLoginRequest kakaoLoginRequest) {
        String accessToken = kakaoLoginRequest.getAccessToken();
        HashMap<String, Object> kakaoUserInfo = new HashMap<>();

        try {
            URL url = new URL(kakaoUserInfoUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = "";
                String responseBody = "";

                while ((line = br.readLine()) != null) {
                    responseBody += line;
                }

                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(responseBody);

                JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
                JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

                String name = kakaoAccount.getAsJsonObject().get("name").getAsString();
                String email = kakaoAccount.getAsJsonObject().get("email").getAsString();
                String image = properties.getAsJsonObject().get("profile_image").getAsString();

                kakaoUserInfo.put("name", name);
                kakaoUserInfo.put("email", email);
                kakaoUserInfo.put("image", image);

                br.close();
            } else {
                throw new KakaoFailException(UserConstants.EOAuth2UserServiceImpl.eKakaoLoginFailException.getValue());
            }
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return kakaoUserInfo;
    }

    private User saveOrUpdateKakaoUser(HashMap<String, Object> kakaoUserInfo) {
        User user = userRepository.findByUserEmailAndUserSocialProvider(kakaoUserInfo.get("email").toString(), UserConstants.ESocialProvider.eKakao)
                .map(entity -> entity.updateKakaoUser(kakaoUserInfo.get(kakaoUserInfo.get("name")).toString(),
                        kakaoUserInfo.get("image").toString()))
                .orElse(User.toEntityOfKakaoUser(kakaoUserInfo));
        return userRepository.save(user);
    }
}
