package com.example.tlover.domain.user.controller;


import com.example.tlover.domain.user.dto.*;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user.service.OAuth2UserService;
import com.example.tlover.domain.user.service.OAuth2UserServiceKakao;
import com.example.tlover.domain.user.service.OAuth2UserServiceGoogle;
import com.example.tlover.domain.user.exception.DeniedAccessExceptioin;
import com.example.tlover.domain.user.service.UserService;
import com.example.tlover.global.jwt.service.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Api(tags = "User API")
public class UserApiController {

    private final UserService userService;
    private final JwtService jwtService;
    private final OAuth2UserService oAuth2UserService;
    private final OAuth2UserServiceKakao oAuth2UserServiceKakao;
    private final OAuth2UserServiceGoogle oAuth2UserServiceGoogle;


    /**
     * 회원 관련 Api
     */
    @ApiOperation(value = "사용자 로그인", notes = "로그인을 합니다.")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest,
                                                   HttpServletRequest request) {
        // 토큰 생성
        //String accessJwt = jwtService.createAccessJwt(loginRequest.getLoginId());
        //String refreshJwt = jwtService.createRefreshJwt(loginRequest.getLoginId());

        User user = userService.loginUser(loginRequest);
        request.getSession().setAttribute("loginId", user.getUserLoginId());

        return ResponseEntity.ok(LoginResponse.from(user));
    }

    @ApiOperation(value = "사용자 회원가입", notes = "회원가입을 합니다.")
    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signupUser(@Valid @RequestBody SignupRequest signUpRequest) {
        User user = userService.insertUser(signUpRequest);

        return ResponseEntity.ok(SignupResponse.builder()
                .message(user.getUserNickName() + "님, 회원가입이 완료되었습니다.")
                .build());
    }

    @ApiOperation(value = "아이디 중복확인", notes = "아이디 중복확인을 합니다.")
    @PostMapping("/duplicate-check")
    public ResponseEntity<DuplicateResponse> duplicateCheckUser(@Valid @RequestBody DuplicateRequest duplicateRequest) {

        userService.duplicateCheck(duplicateRequest);

        return ResponseEntity.ok(DuplicateResponse.builder()
                .message("사용가능한 아이디입니다.")
                .build());
    }


    @ApiOperation(value = "사용자 정보 조회", notes = "사용자 정보 조회를 합니다.")
    @GetMapping("/profile")
    public ResponseEntity<ProfileResponse> getUserProfile(HttpServletRequest request) {
        String loginId = getLoginIdFromSession(request);
        User user = userService.getUserProfile(loginId);
        return ResponseEntity.ok(ProfileResponse.from(user));
    }

    @ApiOperation(value = "아이디 찾기", notes = "아이디 찾기를 합니다.")
    @PostMapping("/find-id")
    public ResponseEntity<FindIdResponse> findUserId(@Valid @RequestBody FindIdRequest findIdRequest) {
        User user = userService.findUserId(findIdRequest);

        return ResponseEntity.ok(FindIdResponse.builder()
                .userId(user.getUserId())
                .build());
    }

    @ApiOperation(value = "비밀번호 재설정", notes = "비밀번호를 재설정 합니다.")
    @PostMapping("/reset-password")
    public ResponseEntity<ResetPasswordResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest,
                                                               HttpServletRequest request) {
        String loginId = getLoginIdFromSession(request);
        userService.resetPassword(resetPasswordRequest, loginId);

        return ResponseEntity.ok(ResetPasswordResponse.builder()
                .message("비밀번호 변경이 완료되었습니다.")
                .build());
    }

    @ApiOperation(value = "사용자 로그아웃", notes = "사용자 로그아웃을 합니다.")
    @GetMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();

        return ResponseEntity.ok("로그아웃에 성공했습니다.");
    }

    // 세션에 저장된 로그인 아이디 얻기
    public String getLoginIdFromSession(HttpServletRequest request) {
        Object loginId = request.getSession().getAttribute("loginId");
        if (loginId == null) throw new DeniedAccessExceptioin();

        return loginId.toString();
    }

    /**
     * OAuth2
     */

    @ApiOperation(value = "네이버 로그인", notes = "네이버 로그인을 합니다.")
    @PostMapping("/naver-login")
    public ResponseEntity<LoginResponse> loginNaverUser(@Valid @RequestBody NaverLoginRequest naverLoginRequest){
        LoginResponse loginResponse = oAuth2UserService.validateNaverAccessToken(naverLoginRequest);
        // 나중에 시큐리티, JWT 구현된다면 HTTP 응답 헤더에 엑세스토큰 추가!
        return ResponseEntity.ok(loginResponse);
    }

    @ApiOperation(value = "구글 로그인", notes = "구글 로그인을 합니다.")
    @PostMapping("/google-login")
    public ResponseEntity<LoginResponse> loginGoogleUser(@Valid @RequestBody GoogleLoginRequest googleLoginRequest){
        LoginResponse loginResponse = oAuth2UserServiceGoogle.validateGoogleAccessToken(googleLoginRequest);
        // 나중에 시큐리티, JWT 구현된다면 HTTP 응답 헤더에 엑세스토큰 추가!
        return ResponseEntity.ok(loginResponse);
    }

    /**
     * 카카오 로그인 API
     * [POST] api/v1/users/kakao-login
     * @param kakaoLoginRequest
     * @return ResponseEntity
     * @author hyeseon
     */
    @ApiOperation(value = "카카오 로그인", notes = "카카오 로그인을 합니다.")
    @PostMapping("/kakao-login")
    public ResponseEntity<LoginResponse> loginKakaoUser(@Valid @RequestBody KakaoLoginRequest kakaoLoginRequest) {
        LoginResponse loginResponse = oAuth2UserServiceKakao.validateKakaoAccessToken(kakaoLoginRequest);
        return ResponseEntity.ok(loginResponse);
    }
}