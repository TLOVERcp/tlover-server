package com.example.tlover.domain.user.controller;


import com.example.tlover.domain.diary.exception.NotFoundDiaryException;
import com.example.tlover.domain.rating.dto.RatingGetResponse;
import com.example.tlover.domain.rating.service.RatingService;
import com.example.tlover.domain.region.exception.NotFoundRegionNameException;
import com.example.tlover.domain.thema.exception.NotFoundThemaNameException;
import com.example.tlover.domain.user.dto.*;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user.exception.*;
import com.example.tlover.domain.user.exception.oauth2.naver.*;
import com.example.tlover.domain.user.service.OAuth2UserServiceKakao;
import com.example.tlover.domain.user.service.OAuth2UserServiceNaver;
import com.example.tlover.domain.user.service.OAuth2UserServiceGoogle;
import com.example.tlover.domain.user.service.UserService;
import com.example.tlover.domain.user_refreshtoken.service.UserRefreshTokenService;
import com.example.tlover.domain.user_region.dto.UpdateUserRegionRequest;
import com.example.tlover.domain.user_region.dto.UserRegionResponse;
import com.example.tlover.domain.user_thema.service.UserThemaService;
import com.example.tlover.domain.user_region.service.UserRegionService;
import com.example.tlover.global.jwt.service.JwtService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Api(tags = "User API")
public class UserApiController {

    private final UserRegionService userRegionService;
    private final UserService userService;
    private final JwtService jwtService;
    private final UserRefreshTokenService userRefreshTokenService;
    private final OAuth2UserServiceNaver oAuth2UserServiceNaver;
    private final OAuth2UserServiceKakao oAuth2UserServiceKakao;
    private final OAuth2UserServiceGoogle oAuth2UserServiceGoogle;
    private final UserThemaService userThemaService;
    private final RatingService ratingService;


    /**
     * 사용자 로그인
     * @param loginRequest, request
     * @return ResponseEntity<LoginResponse>
     * @author 윤여찬, 토큰관련 : 한규범
     */

    @ApiOperation(value = "사용자 로그인", notes = "로그인을 합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "비밀번호가 잘못되었습니다.(U0001) / 해당 아이디를 찾을 수 없습니다.(U0002) / 해당 계정은 삭제된 계정입니다.(U0010)", response = InvalidPasswordException.class),
            @ApiResponse(code = 400, message = "해당 아이디를 찾을 수 없습니다.(U0002)", response = NotFoundUserException.class),
            @ApiResponse(code = 400, message = "해당 계정은 삭제된 계정입니다.(U0010)", response = UserDeletedException.class)
    })
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
                .userNickname(user.getUserNickName())
                .message("로그인에 성공하였습니다.")
                .build());
    }

    /**
     * 사용자 회원가입
     * @param signUpRequest
     * @return ResponseEntity<SignupResponse>
     * @author 윤여찬 , 사용자 관심지역 관련: 정혜선
     */
    @ApiOperation(value = "사용자 회원가입", notes = "회원가입을 합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 409, message = "해당 아이디는 이미 존재하는 아이디입니다.(U0003) / 해당 전화번호는 이미 존재하는 전화번호입니다.(U0008) / 해당 닉네임은 이미 존재하는 닉네임입니다.(U0009)", response = UserIdDuplicateException.class),
            @ApiResponse(code = 409, message = "해당 전화번호는 이미 존재하는 전화번호입니다.(U0008)", response = PhoneNumDuplicateException.class),
            @ApiResponse(code = 409, message = "해당 닉네임은 이미 존재하는 닉네임입니다.(U0009)", response = UserNicknameDuplicateException.class),
            @ApiResponse(code = 400, message = "해당 테마 이름을 찾지 못했습니다.(T0001) / 해당 지역 이름을 찾지 못했습니다.(R0001)", response = NotFoundThemaNameException.class),
            @ApiResponse(code = 400, message = "해당 지역 이름을 찾지 못했습니다.(R0001)", response = NotFoundRegionNameException.class)
    })
    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signupUser(@Valid @RequestBody SignupRequest signUpRequest) {

        userThemaService.checkThemaName(signUpRequest.getUserThemaName());
        userRegionService.checkRegionName(signUpRequest.getUserRegions());

        User user = userService.insertUser(signUpRequest);
        userRegionService.createUserRegion(signUpRequest, user.getUserId());
        userThemaService.insertUserThema(signUpRequest.getUserThemaName(), user);
        ratingService.createRating(user);
        return ResponseEntity.ok(SignupResponse.builder()
                .message(user.getUserNickName() + "님, 회원가입이 완료되었습니다.")
                .build());
    }

    /**
     * 아이디 중복확인
     * @param duplicateRequest
     * @return ResponseEntity<MessageResponse>
     * @author 윤여찬
     */
    @ApiOperation(value = "아이디 중복확인", notes = "아이디 중복확인을 합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 409, message = "해당 아이디는 이미 존재하는 아이디입니다.(U0003)", response = UserIdDuplicateException.class)
    })
    @PostMapping("/duplicate-check")
    public ResponseEntity<MessageResponse> duplicateCheckUser(@Valid @RequestBody DuplicateRequest duplicateRequest) {

        userService.loginIdDuplicateCheck(duplicateRequest.getLoginId());

        return ResponseEntity.ok(MessageResponse.builder()
                .message("사용가능한 아이디입니다.")
                .build());
    }

    /**
     * 닉네임 중복확인
     * @param nicknameDuplicateRequest
     * @return ResponseEntity<MessageResponse>
     * @author 윤여찬
     */
    @ApiOperation(value = "닉네임 중복확인", notes = "닉네임 중복확인을 합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 409, message = "해당 닉네임은 이미 존재하는 닉네임입니다.(U0009)", response = UserNicknameDuplicateException.class)
    })
    @PostMapping("/nickname-duplicate-check")
    public ResponseEntity<MessageResponse> duplicateCheckUser(@Valid @RequestBody NicknameDuplicateRequest nicknameDuplicateRequest) {

        userService.userNicknameDuplicateCheck(nicknameDuplicateRequest.getUserNickname());

        return ResponseEntity.ok(MessageResponse.builder()
                .message("사용가능한 닉네임입니다.")
                .build());
    }

    /**
     * 사용자 정보 조회
     * @param
     * @return
     * @author 윤여찬
     */
    @ApiOperation(value = "사용자 정보 조회", notes = "사용자 정보를 조회합니다.")
    @GetMapping("/profile")
    public ResponseEntity<ProfileResponse> getUserProfile() {
        String loginId = this.jwtService.getLoginId();
        User user = userService.getUserProfile(loginId);
        List<String> userThemaName = userThemaService.getUserThemaName(user.getUserId());
        List<String> userRegionName = new ArrayList<>();
        List<UserRegionResponse> regions = userRegionService.getUserRegion(user.getUserLoginId());

        for (UserRegionResponse response : regions) {
            userRegionName.add(response.getRegionName());
        }

        RatingGetResponse response = ratingService.getRating(user.getUserLoginId());

        return ResponseEntity.ok(ProfileResponse.from(user, userThemaName, userRegionName, response.getRating()));
    }

    /**
     * 사용자 정보 수정
     * @param userProfileRequest, file, request
     * @return ResponseEntity<String>
     * @author 윤여찬
     */
    @ApiOperation(value = "사용자 정보 수정", notes = "사용자 정보를 수정합니다.", produces = "multipart/form-data")
    @ApiResponses(value = {
            @ApiResponse(code = 409, message = "해당 닉네임은 이미 존재하는 닉네임입니다.(U0009) / 해당 이메일은 이미 존재하는 이메일입니다.(U0011)", response = UserNicknameDuplicateException.class),
            @ApiResponse(code = 409, message = "해당 이메일은 이미 존재하는 이메일입니다.(U0011)", response = UserEmailDuplicateException.class)
    })
    @PostMapping("/update-profile")
    public ResponseEntity<MessageResponse> updateUserProfile(@Valid @ModelAttribute UserProfileRequest userProfileRequest,
                                                    @RequestParam(required = false) MultipartFile file) {

        userThemaService.checkThemaName(userProfileRequest.getUserThemaName());
        List<String> list = userProfileRequest.getUserRegionName();
        String[] userRegionName = list.toArray(new String[list.size()]);
        userRegionService.checkRegionName(userProfileRequest.getUserRegionName().toArray(userRegionName));

        String loginId = jwtService.getLoginId();
        User user = userService.updateUserProfile(loginId, userProfileRequest, file);
        userThemaService.updateUserThema(userProfileRequest.getUserThemaName(), user);

        UpdateUserRegionRequest request = new UpdateUserRegionRequest();
        request.setUserRegions(userRegionName);
        userRegionService.updateUserRegion(request, user.getUserLoginId());

        return ResponseEntity.ok(MessageResponse.builder()
                .message("사용자 정보가 수정되었습니다.")
                .build());
    }

    /**
     * 아이디 찾기
     * @param findIdRequest, request
     * @return ResponseEntity<FindIdResponse>
     * @author 윤여찬
     */
    @ApiOperation(value = "아이디 찾기", notes = "아이디 찾기를 합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "해당 아이디를 찾을 수 없습니다.(U0002) / 인증 코드가 일치하지 않습니다.(U0007)", response = NotFoundUserException.class),
            @ApiResponse(code = 400, message = "인증 코드가 일치하지 않습니다.(U0007)", response = NotCertifiedValueException.class)
    })
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
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "해당 아이디를 찾을 수 없습니다.(U0002) / 입력하신 비밀번호가 기존의 비밀번호와 일치하지 않습니다.(U0005) / 변경할 비밀번호가 기존의 비밀번호와 동일합니다.(U0006)", response = NotFoundUserException.class),
            @ApiResponse(code = 400, message = "입력하신 비밀번호가 기존의 비밀번호와 일치하지 않습니다.(U0005)", response = NotEqualPasswordException.class),
            @ApiResponse(code = 400, message = "변경할 비밀번호가 기존의 비밀번호와 동일합니다.(U0006)", response = PasswordEqualException.class),
    })
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
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "입력하신 비밀번호가 기존의 비밀번호와 일치하지 않습니다.(U0005) / 변경할 비밀번호가 기존의 비밀번호와 동일합니다.(U0006)", response = NotEqualPasswordException.class),
            @ApiResponse(code = 400, message = "변경할 비밀번호가 기존의 비밀번호와 동일합니다.(U0006)", response = PasswordEqualException.class),
    })
    @PostMapping("/reset-password")
    public ResponseEntity<ResetPasswordResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        String loginId = jwtService.getLoginId();
        userService.resetPassword(resetPasswordRequest, loginId);

        return ResponseEntity.ok(ResetPasswordResponse.builder()
                .message("비밀번호 변경이 완료되었습니다.")
                .build());
    }

    /**
     * 사용자 로그아웃
     * @param request
     * @return ResponseEntity<MessageResponse>
     * @author 윤여찬
     */
    @ApiOperation(value = "사용자 로그아웃", notes = "사용자 로그아웃을 합니다.")
    @GetMapping("/logout")
    public ResponseEntity<MessageResponse> logoutUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();

        return ResponseEntity.ok(MessageResponse.builder()
                .message("로그아웃에 성공했습니다.")
                .build());
    }

    /**
     * 회원 탈퇴
     * @param withdrawUserRequest, request
     * @return ResponseEntity<MessageResponse>
     * @author 윤여찬
     */
    @ApiOperation(value = "회원 탈퇴", notes = "회원 탈퇴를 합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "입력하신 비밀번호가 기존의 비밀번호와 일치하지 않습니다.(U0005)", response = NotEqualPasswordException.class)
    })
    @PostMapping("/withdraw")
    public ResponseEntity<MessageResponse> withdrawUser(@Valid @RequestBody WithdrawUserRequest withdrawUserRequest) {
        String loginId = jwtService.getLoginId();
        userService.withdrawUser(withdrawUserRequest, loginId);

        return ResponseEntity.ok(MessageResponse.builder()
                .message("회원 탈퇴가 완료되었습니다.")
                .build());
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
    @ApiResponses(value = {
            @ApiResponse(code = 409 , message = "[N0001] NAVER API 응답을 읽는데 실패했습니다.", response = NaverApiResponseException.class),
            @ApiResponse(code = 409 , message = "[N0002] NAVER API URL이 잘못되었습니다. : ", response = NaverApiUrlException.class),
            @ApiResponse(code = 401 , message = "[N0003] Naver 인증에 실패했습니다.", response = NaverAuthenticationFailedException.class),
            @ApiResponse(code = 409 , message = "[N0004] NAVER와의 연결이 실패했습니다. : ", response = NaverConnectionException.class),
            @ApiResponse(code = 404 , message = "[N0005] Naver API 검색 결과가 없습니다.", response = NaverNotFoundException.class),
            @ApiResponse(code = 403 , message = "[N0006] Naver API 호출 권한이 없습니다.", response = NaverPermissionException.class),
    })
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
     * @author 정혜선
     */
    @ApiOperation(value = "카카오 로그인", notes = "카카오 로그인을 합니다.")
    @PostMapping("/kakao-login")
    public ResponseEntity<LoginResponse> loginKakaoUser(@Valid @RequestBody KakaoLoginRequest kakaoLoginRequest) {
        LoginResponse loginResponse = oAuth2UserServiceKakao.validateKakaoAccessToken(kakaoLoginRequest);
        return ResponseEntity.ok(loginResponse);
    }



}