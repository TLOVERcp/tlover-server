package com.example.tlover.domain.user.controller;


import com.example.tlover.domain.user.dto.LoginRequest;
import com.example.tlover.domain.user.dto.LoginResponse;
import com.example.tlover.domain.user.dto.NaverLoginRequest;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user.service.OAuth2UserService;
import com.example.tlover.domain.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Api(tags = "User API")
public class UserApiController {

    private final UserService userService;
    private final OAuth2UserService oAuth2UserService;


    @ApiOperation(value = "사용자 로그인", notes = "로그인을 합니다.")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest, HttpSession session) {
        User user = userService.loginUser(loginRequest);
        session.setAttribute("loginId", user.getUserId());
        return ResponseEntity.ok(LoginResponse.from(user));
    }

//    @ApiOperation(value = "사용자 회원가입", notes = "회원가입을 합니다.")
//    @PostMapping("/signup")
//    public ResponseEntity<SignupResponse> signupUser(@Valid @RequestBody SignupRequest signUpRequest) {
//        return ResponseEntity.ok(userService.insertUser(signUpRequest));
//    }
//
//    @ApiOperation(value = "아이디 중복확인", notes = "아이디 중복확인을 합니다.")
//    @PostMapping("/deplicate-check")
//    public ResponseEntity<DuplicateResponse> duplicateCheckUser(@Valid @RequestBody DuplicateRequest duplicateRequest) {
//        return ResponseEntity.ok(userService.duplicateCheck(duplicateRequest));
//    }
//
//
//    @ApiOperation(value = "사용자 정보 조회", notes = "사용자 정보 조회를 합니다.")
//    @GetMapping("/profile")
//    public ResponseEntity<ProfileResponse> getUserProfile(HttpSession session) {
//        return ResponseEntity.ok(userService.getUserProfile(session.getAttribute("loginId").toString()));
//    }
//
//    @ApiOperation(value = "아이디 찾기", notes = "아이디 찾기를 합니다.")
//    @GetMapping("/find-id")
//    public ResponseEntity<FindIdResponse> findUserId(@Valid @RequestBody FindIdRequest findIdRequest) {
//
//        return ResponseEntity.ok(userService.findUserId(findIdRequest));
//    }
//
//    @ApiOperation(value = "비밀번호 재설정", notes = "비밀번호를 재설정 합니다.")
//    @GetMapping("/reset-password")
//    public ResponseEntity<ResetPasswordResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
//        return ResponseEntity.ok(userService.resetPassword(resetPasswordRequest));
//
//    }
//
//    @ApiOperation(value = "사용자 로그아웃", notes = "사용자 로그아웃을 합니다.")
//    @GetMapping("/logout")
//    public String logoutUser(Model model) {
//        return null;
//    }


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



}