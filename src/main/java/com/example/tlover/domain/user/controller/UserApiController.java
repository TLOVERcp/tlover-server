package com.example.tlover.domain.user.controller;


import com.example.tlover.domain.user.dto.*;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user.exception.NotCertifiedValueException;
import com.example.tlover.domain.user.service.OAuth2UserServiceKakao;
import com.example.tlover.domain.user.service.OAuth2UserServiceNaver;
import com.example.tlover.domain.user.service.OAuth2UserServiceGoogle;
import com.example.tlover.domain.user.exception.DeniedAccessExceptioin;
import com.example.tlover.domain.user.service.UserService;
import com.example.tlover.domain.user_refreshtoken.service.UserRefreshTokenService;
import com.example.tlover.domain.user_thema.repository.UserThemaRepository;
import com.example.tlover.domain.user_thema.service.UserThemaService;
import com.example.tlover.global.jwt.service.JwtService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;


@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Api(tags = "User API")
public class UserApiController {

    private final UserService userService;
    private final JwtService jwtService;
    private final UserRefreshTokenService userRefreshTokenService;
    private final OAuth2UserServiceNaver oAuth2UserServiceNaver;
    private final OAuth2UserServiceKakao oAuth2UserServiceKakao;
    private final OAuth2UserServiceGoogle oAuth2UserServiceGoogle;
    private final UserThemaService userThemaService;


    /**
     * 사용자 로그인
     * @param loginRequest, request
     * @return ResponseEntity<LoginResponse>
     * @author 윤여찬, 토큰관련 : 한규범
     */
    @ApiOperation(value = "사용자 로그인", notes = "로그인을 합니다.")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest,
                                                   HttpServletRequest request) {

        User user = userService.loginUser(loginRequest);
        request.getSession().setAttribute("loginId", user.getUserLoginId());

        // 토큰 생성
        String accessJwt = jwtService.createAccessJwt(loginRequest.getLoginId());
        String refreshJwt = jwtService.createRefreshJwt(loginRequest.getLoginId());
        //유저 리프레시 토큰 저장
        long refreshJwtIdx = userRefreshTokenService.insertRefreshToken(refreshJwt, user);

        return ResponseEntity.ok(LoginResponse.builder()
                .accessJwt(accessJwt)
                .refreshJwtIdx(refreshJwtIdx)
                .message("로그인에 성공하였습니다.")
                .build());
    }

    /**
     * 사용자 회원가입
     * @param signUpRequest
     * @return ResponseEntity<SignupResponse>
     * @author 윤여찬
     */
    @ApiOperation(value = "사용자 회원가입", notes = "회원가입을 합니다.")
    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signupUser(@Valid @RequestBody SignupRequest signUpRequest) {
        User user = userService.insertUser(signUpRequest);
        userThemaService.insertUserThema(signUpRequest.getUserThemaName(), user);

        return ResponseEntity.ok(SignupResponse.builder()
                .message(user.getUserNickName() + "님, 회원가입이 완료되었습니다.")
                .build());
    }

    /**
     * 아이디 중복확인
     * @param duplicateRequest
     * @return ResponseEntity<DuplicateResponse>
     * @author 윤여찬
     */
    @ApiOperation(value = "아이디 중복확인", notes = "아이디 중복확인을 합니다.")
    @PostMapping("/duplicate-check")
    public ResponseEntity<DuplicateResponse> duplicateCheckUser(@Valid @RequestBody DuplicateRequest duplicateRequest) {

        userService.loginIdDuplicateCheck(duplicateRequest.getLoginId());

        return ResponseEntity.ok(DuplicateResponse.builder()
                .message("사용가능한 아이디입니다.")
                .build());
    }

    /**
     * 사용자 정보 조회
     * @param request
     * @return
     * @author 윤여찬
     */
    @ApiOperation(value = "사용자 정보 조회", notes = "사용자 정보를 조회합니다.")
    @GetMapping("/profile")
    public ResponseEntity<ProfileResponse> getUserProfile(HttpServletRequest request) {
        String loginId = getLoginIdFromSession(request);
        User user = userService.getUserProfile(loginId);
        List<String> userThemaName = userThemaService.getUserThemaName(user.getUserId());

        return ResponseEntity.ok(ProfileResponse.from(user, userThemaName));
    }

    /**
     * 사용자 정보 수정
     * @param userProfileRequest, file, request
     * @return ResponseEntity<String>
     * @author 윤여찬
     */
    @ApiOperation(value = "사용자 정보 수정", notes = "사용자 정보를 수정합니다.", produces = "multipart/form-data")
    @PostMapping("/update-profile")
    public ResponseEntity<String> updateUserProfile(@Valid @ModelAttribute UserProfileRequest userProfileRequest,
                                                    @RequestParam(required = false) MultipartFile file,
                                                    HttpServletRequest request) {
        String loginId = this.getLoginIdFromSession(request);
        User user = userService.updateUserProfile(loginId, userProfileRequest, file);
        userThemaService.updateUserThema(userProfileRequest.getUserThemaName(), user);
        request.getSession().setAttribute("loginId", user.getUserLoginId());

        return ResponseEntity.ok("사용자 정보가 수정되었습니다.");
    }

    /**
     * 아이디 찾기
     * @param findIdRequest, request
     * @return ResponseEntity<FindIdResponse>
     * @author 윤여찬
     */
    @ApiOperation(value = "아이디 찾기", notes = "아이디 찾기를 합니다.")
    @PostMapping("/find-id")
    public ResponseEntity<FindIdResponse> findUserId(@Valid @RequestBody FindIdRequest findIdRequest,
                                                     HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException, JsonProcessingException {
        CertifiedValue certifiedValue = getCertifiedValueFromSession(request);
        FindIdResponse response = userService.findUserId(findIdRequest, certifiedValue);
        request.getSession().invalidate();
        return ResponseEntity.ok(response);
    }

    /**
     * 비밀번호 찾기(재설정)
     * @param findPasswordRequest, request
     * @return ResponseEntity<FindPasswordResponse>
     * @author 윤여찬
     */
    @ApiOperation(value = "비밀번호 찾기(재설정)", notes = "비밀번호 찾기를 합니다.")
    @PostMapping("/find-password")
    public ResponseEntity<FindPasswordResponse> findPassword(@Valid @RequestBody FindPasswordRequest findPasswordRequest,
                                                             HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException, JsonProcessingException {
        CertifiedValue certifiedValue = getCertifiedValueFromSession(request);
        FindPasswordResponse response = userService.findPassword(findPasswordRequest, certifiedValue);
        request.getSession().invalidate();
        return ResponseEntity.ok(response);
    }

    /**
     * 비밀번호 재설정
     * @param resetPasswordRequest, request
     * @return ResponseEntity<ResetPasswordResponse>
     * @author 윤여찬
     */
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

    /**
     * 사용자 로그아웃
     * @param request
     * @return ResponseEntity<String>
     * @author 윤여찬
     */
    @ApiOperation(value = "사용자 로그아웃", notes = "사용자 로그아웃을 합니다.")
    @GetMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();

        return ResponseEntity.ok("로그아웃에 성공했습니다.");
    }

    /**
     * 회원 탈퇴
     * @param withdrawUserRequest, request
     * @return ResponseEntity<String>
     * @author 윤여찬
     */
    @ApiOperation(value = "회원 탈퇴", notes = "회원 탈퇴를 합니다.")
    @PostMapping("/withdraw")
    public ResponseEntity<String> withdrawUser(@Valid @RequestBody WithdrawUserRequest withdrawUserRequest,
                                               HttpServletRequest request) {
        String loginId = getLoginIdFromSession(request);
        userService.withdrawUser(withdrawUserRequest, loginId);

        return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
    }

    /**
     * 세션에 저장된 로그인 아이디 얻기
     * @param request
     * @return String
     * @author 윤여찬
     */
    public String getLoginIdFromSession(HttpServletRequest request) {
        Object loginId = request.getSession().getAttribute("loginId");

        if (loginId == null) throw new DeniedAccessExceptioin();

        return loginId.toString();

    }

    /**
     * 세션에 저장된 인증코드 얻기
     * @param request
     * @return CertifiedValue
     * @author 윤여찬
     */
    public CertifiedValue getCertifiedValueFromSession(HttpServletRequest request) {
        Object certifiedValue = request.getSession().getAttribute("certifiedValue");
        if (certifiedValue == null) throw new NotCertifiedValueException();

        return (CertifiedValue) certifiedValue;
    }

    /**
     * OAuth2
     */

    @ApiOperation(value = "네이버 로그인", notes = "네이버 로그인을 합니다.")
    @PostMapping("/naver-login")
    public ResponseEntity<LoginResponse> loginNaverUser(@Valid @RequestBody NaverLoginRequest naverLoginRequest){
        LoginResponse loginResponse = oAuth2UserServiceNaver.validateNaverAccessToken(naverLoginRequest);
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