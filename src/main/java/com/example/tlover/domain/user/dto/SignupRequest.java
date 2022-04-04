package com.example.tlover.domain.user.dto;


import com.example.tlover.domain.user.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "회원가입을 위한 요청 객체")
public class SignupRequest {

    @NotBlank(message = "회원의 로그인Id를 입력해주세요.")
    @Size(min = 5, max = 18, message = "로그인 Id는 크기가 5에서 18사이여야 합니다.")
    @ApiModelProperty(notes = "로그인 Id를 입력해 주세요.")
    private String loginId;

    @NotBlank(message = "회원의 비밀번호를 입력해 주세요.")
    @Size(min = 7, max = 20, message = "패스워드는 7글자 이상 16글자 이하여야 합니다.")
    @ApiModelProperty(notes = "회원의 비밀번호를 입력해 주세요.")
    private String password;

    @NotBlank(message = "회원의 이메일을 입력해 주세요.")
    @ApiModelProperty(notes = "회원의 이메일을 입력해 주세요.")
    private String userEmail;

    @NotBlank(message = "회원의 전화번호를 입력해 주세요.")
    @Size(min = 10, max = 11, message = "패스워드는 10글자 이상 11글자 이하여야 합니다.")
    @ApiModelProperty(notes = "회원의 전화번호를 입력해 주세요.")
    private String userPhoneNum;

    @NotBlank(message = "회원의 닉네임을 입력해 주세요.")
    @Size(min = 4, max = 16, message = "패스워드는 4글자 이상 16글자 이하여야 합니다.")
    @ApiModelProperty(notes = "회원의 닉네임을 입력해 주세요.")
    private String userNickName;

    public User toEntity(String encodePassword) {
        User user = new User();
        user.setUserLoginId(this.loginId);
        user.setUserPassword(encodePassword);
        user.setUserPhoneNum(this.userPhoneNum);
        user.setUserEmail(this.userEmail);
        user.setUserNickName(this.userNickName);
        return user;
    }
}