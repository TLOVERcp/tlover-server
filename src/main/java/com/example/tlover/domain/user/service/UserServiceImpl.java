package com.example.tlover.domain.user.service;


import com.example.tlover.domain.myfile.entity.MyFile;
import com.example.tlover.domain.myfile.service.MyFileService;
import com.example.tlover.domain.user.dto.*;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user.exception.*;
import com.example.tlover.domain.user.repository.UserRepository;
import com.example.tlover.global.encryption.SHA256Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final SHA256Util sha256Util;
    private final MyFileService myFileService;

    /**
     * 로그인
     * @param loginRequest
     * @return User
     * @author 윤여찬
     */
    @Override
    public User loginUser(LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByUserLoginId(loginRequest.getLoginId());

        if (user.isEmpty()) throw new NotFoundUserException();
        if (!user.get().getUserPassword().equals(sha256Util.encrypt(loginRequest.getPassword()))) throw new InvalidPasswordException();
        if (user.get().getUserState().equals("DELETED")) throw new UserDeletedException();
        return user.get();
    }

    /**
     * 회원가입
     * @param signupRequest
     * @return User
     * @author 윤여찬
     */
    @Override
    public User insertUser(SignupRequest signupRequest) {

        User user = signupRequest.toEntity(sha256Util.encrypt(signupRequest.getPassword()));
        this.loginIdDuplicateCheck(user.getUserLoginId());
        this.userNicknameDuplicateCheck(user.getUserNickName());
        this.phoneNumDuplicateCheck(user.getUserPhoneNum());

        user.setUserState("active");
        userRepository.save(user);

        return userRepository.findByUserLoginId(user.getUserLoginId()).get();
    }

    /**
     * 아이디 중복 확인
     * @param loginId
     * @return
     * @author 윤여찬
     */
    @Override
    public void loginIdDuplicateCheck(String loginId) {
        Optional<User> user = userRepository.findByUserLoginId(loginId);

        if (!user.isEmpty()) throw new UserIdDuplicateException();
    }

    /**
     * 닉네임 중복 확인
     * @param userNickName
     * @return
     * @author 윤여찬
     */
    @Override
    public void userNicknameDuplicateCheck(String userNickName) {
        Optional<User> user = userRepository.findByUserNickName(userNickName);

        if (!user.isEmpty()) throw new UserNicknameDuplicateException();
    }

    /**
     * 전화번호 중복 확인
     * @param phoneNum
     * @return
     * @author 윤여찬
     */
    @Override
    public void phoneNumDuplicateCheck(String phoneNum) {
        Optional<User> user = userRepository.findByUserPhoneNum(phoneNum);

        if (!user.isEmpty()) throw new PhoneNumDuplicateException();
    }

    /**
     * 이메일 중복 확인
     * @param userEmail
     * @return
     * @author 윤여찬
     */
    @Override
    public void userEmailDuplicateCheck(String userEmail) {
        Optional<User> user = userRepository.findByUserEmail(userEmail);

        if (!user.isEmpty()) throw new UserEmailDuplicateException();
    }

    /**
     * 아이디 찾기
     * @param findIdRequest, certifiedValue
     * @return FindIdResponse
     * @author 윤여찬
     */
    @Override
    public FindIdResponse findUserId(FindIdRequest findIdRequest, CertifiedValue certifiedValue) {

        if ( !certifiedValue.getFindLoginId().equals(findIdRequest.getCertifiedValue()) )throw new NotCertifiedValueException();

        // 인증 코드가 일치할 경우
        Optional<User> user = userRepository.findByUserPhoneNum(findIdRequest.getUserPhoneNum());

        if (user.isEmpty()) throw new NotFoundUserException();

        return FindIdResponse.builder()
                .loginId(user.get().getUserLoginId())
                .message("아이디 찾기가 완료되었습니다.")
                .build();

    }

    /**
     * 비밀번호 찾기
     * @param findPasswordRequest, certifiedValue
     * @return FindPasswordResponse
     * @author 윤여찬
     */
    @Override
    @Transactional
    public FindPasswordResponse findPassword(FindPasswordRequest findPasswordRequest, CertifiedValue certifiedValue) {

        // 인증 코드 확인
        if ( !certifiedValue.getFindPassword().equals(findPasswordRequest.getCertifiedValue()) )throw new NotCertifiedValueException();

        // 인증 코드가 일치할 경우
        Optional<User> user = this.userRepository.findByUserLoginId(findPasswordRequest.getLoginId());
        if (user.isEmpty()) throw new NotFoundUserException();

        // 변경할 비밀번호가 기존 비밀번호와 일치할 때
        if (user.get().getUserPassword().equals(sha256Util.encrypt(findPasswordRequest.getChangePassword())))
            throw new PasswordEqualException();

        user.get().setUserPassword(sha256Util.encrypt(findPasswordRequest.getChangePassword()));

        return FindPasswordResponse.builder()
                .message("비밀번호 재설정이 완료되었습니다.")
                .build();

    }

    /**
     * 비밀번호 재설정
     * @param resetPasswordRequest, loginId
     * @return User
     * @author 윤여찬
     */
    @Override
    @Transactional
    public User resetPassword(ResetPasswordRequest resetPasswordRequest, String loginId) {
        User user = userRepository.findByUserLoginId(loginId).get();

        // 기존 비밀번호가 일치하지 않을 때
        if (!user.getUserPassword().equals(sha256Util.encrypt(resetPasswordRequest.getBeforePassword())))
            throw new NotEqualPasswordException();

        // 변경할 비밀번호가 기존 비밀번호와 일치할 때
        if (user.getUserPassword().equals(sha256Util.encrypt(resetPasswordRequest.getAfterPassword())))
            throw new PasswordEqualException();

        user.setUserPassword(sha256Util.encrypt(resetPasswordRequest.getAfterPassword()));
        return user;
    }

    /**
     * 사용자 정보 조회
     * @param loginId
     * @return User
     * @author 윤여찬
     */
    @Override
    public User getUserProfile(String loginId) { return userRepository.findByUserLoginId(loginId).get();}

    /**
     * 사용자 정보 수정
     * @param loginId, userProfileRequest, file
     * @return User
     * @author 윤여찬
     */
    @Override
    @Transactional
    public User updateUserProfile(String loginId, UserProfileRequest profileRequest, MultipartFile file) {
        User user = this.getUserProfile(loginId);

        // if (!loginId.equals(profileRequest.getLoginId())) this.loginIdDuplicateCheck(profileRequest.getLoginId());
        if (!user.getUserNickName().equals(profileRequest.getUserNickName())) this.userNicknameDuplicateCheck(profileRequest.getUserNickName());
        if (!user.getUserEmail().equals(profileRequest.getUserEmail())) this.userEmailDuplicateCheck(profileRequest.getUserEmail());

        // user.setUserLoginId(profileRequest.getLoginId());
        user.setUserEmail(profileRequest.getUserEmail());
        user.setUserNickName(profileRequest.getUserNickName());

        if (file != null) {
            MyFile profileImg = myFileService.saveImage(file);
            user.setUserProfileImg(profileImg.getFileKey());
        }

        return user;

    }

    /**
     * 회원 탈퇴
     * @param withdrawUserRequest, loginId
     * @return
     * @author 윤여찬
     */
    @Override
    @Transactional
    public void withdrawUser(WithdrawUserRequest withdrawUserRequest, String loginId) {
        User user = this.getUserProfile(loginId);

        if (!user.getUserPassword().equals(sha256Util.encrypt(withdrawUserRequest.getPassword()))) throw new NotEqualPasswordException();
        user.setUserState("DELETED");
    }

    public User getUserByUserId(Long userId) {
        return this.userRepository.findByUserId(userId).orElseThrow(NotFoundUserException::new);
    }


}
