package com.example.tlover.domain.user.service;

import com.example.tlover.domain.user.constant.UserConstants.EOAuth2UserServiceImpl;
import com.example.tlover.domain.user.constant.UserConstants.ESocialProvider;
import com.example.tlover.domain.user.dto.LoginResponse;
import com.example.tlover.domain.user.dto.NaverLoginRequest;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user.exception.oauth2.naver.*;
import com.example.tlover.domain.user.repository.UserRepository;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2UserServiceNaverImpl implements OAuth2UserServiceNaver {

    private final UserRepository userRepository;

    @Value("${spring.security.oauth2.client.provider.naver.user-info-uri}")
    private String naverUserInfoUrl;

    /**
     * Naver Login Or Sign Up
     */

    @Override
    public LoginResponse validateNaverAccessToken(NaverLoginRequest naverLoginRequest) {
        String naverResponseBody = createNaverResponseBody(naverUserInfoUrl, createNaverRequestHeader(naverLoginRequest.getAccessToken()));
        HashMap<String, Object> naverUserInfo = getNaverUserInfo(naverResponseBody);
        // 나중에 시큐리티, JWT 토큰 생성 관련 로직 추가할 예정!
        return LoginResponse.from(saveOrUpdateNaverUser(naverUserInfo));
    }
    private Map<String, String> createNaverRequestHeader(String accessToken) {
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put(EOAuth2UserServiceImpl.eAuthorization.getValue(), createNaverBearerHeader(accessToken));
        return requestHeaders;}
    private String createNaverBearerHeader(String accessToken) {
        return EOAuth2UserServiceImpl.eBearer.getValue() + accessToken;}
    private String createNaverResponseBody(String naverUserInfoUrl, Map<String, String> requestHeaders) {
        HttpURLConnection con = connect(naverUserInfoUrl);
        try {
            con.setRequestMethod(EOAuth2UserServiceImpl.eGetMethod.getValue());
            for(Map.Entry<String, String> header : requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());}
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) return readBody(con.getInputStream());
            else if(responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) throw new NaverAuthenticationFailedException();
            else if(responseCode == HttpURLConnection.HTTP_FORBIDDEN) throw new NaverPermissionException();
            else if(responseCode == HttpURLConnection.HTTP_NOT_FOUND) throw new NaverNotFoundException();
            else return readBody(con.getErrorStream());}
        catch (IOException e) { throw new NaverApiResponseException();}
        finally { con.disconnect();}}
    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();}
        catch (MalformedURLException e) {throw new NaverApiUrlException();}
        catch (IOException e) { throw new NaverConnectionException();}}
    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);
        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();
            String line;
            while ((line = lineReader.readLine()) != null)
                responseBody.append(line);
            return responseBody.toString();}
        catch (IOException e) { throw new NaverApiResponseException();}}
    private HashMap<String, Object> getNaverUserInfo(String naverResponseBody) {
        HashMap<String, Object> userInfo = new HashMap<>();
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(naverResponseBody);
        JsonObject jsonContent = element.getAsJsonObject().get(EOAuth2UserServiceImpl.eResponse.getValue()).getAsJsonObject();
        userInfo.put(EOAuth2UserServiceImpl.eNameAttribute.getValue(), jsonContent.get(EOAuth2UserServiceImpl.eNameAttribute.getValue()).getAsString());
        userInfo.put(EOAuth2UserServiceImpl.eNaverProfileImageAttribute.getValue(), jsonContent.get(EOAuth2UserServiceImpl.eNaverProfileImageAttribute.getValue()).getAsString());
        userInfo.put(EOAuth2UserServiceImpl.eEmailAttribute.getValue(), jsonContent.get(EOAuth2UserServiceImpl.eEmailAttribute.getValue()).getAsString());
        return userInfo;}

    private User saveOrUpdateNaverUser(HashMap<String, Object> naverUserInfo) {
        User user = userRepository.findByUserEmailAndUserSocialProvider(naverUserInfo.get(EOAuth2UserServiceImpl.eEmailAttribute.getValue()).toString(), ESocialProvider.eNaver)
                .map(entity -> entity.updateNaverUser(naverUserInfo.get(EOAuth2UserServiceImpl.eNameAttribute.getValue()).toString(),
                        naverUserInfo.get(EOAuth2UserServiceImpl.eNaverProfileImageAttribute.getValue()).toString()))
                .orElse(User.toEntityOfNaverUser(naverUserInfo));
        return userRepository.save(user);
    }

}
