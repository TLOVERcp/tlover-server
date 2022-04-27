package com.example.tlover.domain.user.dto;


import com.example.tlover.domain.user.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "회원가입을 위한 요청 객체")
public class SignupRequest {

    @NotBlank(message = "회원의 로그인Id를 입력해주세요.")
    @Pattern(regexp = "^[a-z0-9]{6,18}$", message = "로그인 Id는 6~18글자의 영소문자, 숫자만 가능합니다.")
    @ApiModelProperty(notes = "로그인 Id를 입력해 주세요.")
    private String loginId;

    @NotBlank(message = "회원의 비밀번호를 입력해 주세요.")
    @Pattern(regexp = "^.*(?=^.{8,20}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$",
            message = "비밀번호는 특수문자를 포함한 8~20글자의 영대/소문자, 숫자만 가능합니다.")
    @ApiModelProperty(notes = "회원의 비밀번호를 입력해 주세요.")
    private String password;

    @NotBlank(message = "회원의 전화번호를 입력해 주세요.")
    @Pattern(regexp = "^[0-9]{11,11}$", message = "전화번호는 11자리의 숫자만 가능합니다.")
    @ApiModelProperty(notes = "회원의 전화번호를 입력해 주세요.")
    private String userPhoneNum;

    @NotBlank(message = "회원의 닉네임을 입력해주세요.")
    @Pattern(regexp = "^[a-z0-9가-힣]{6,18}$", message = "닉네임은 6~18글자의 영소문자, 숫자, 한글만 가능합니다.")
    @ApiModelProperty(notes = "닉네임을 입력해 주세요.")
    private String userNickName;

    @Size(min = 0, max = 3, message = "관심 지역은 0개 이상 3개 이하로 선택해야 합니다.")
    @ApiModelProperty(notes = "사용자 관심 지역을 입력해주세요")
    private String[] userRegions;

    @NotNull(message = "유제 테마가 null 입니다.")
    @Size(min = 0, max = 3, message = "관심 테마는 0개 이상 3개 이하로 선택해야 합니다.")
    @ApiModelProperty(notes = "사용자 관심 테마를 입력해주세요.")
    private List<String> userThemaName;

    public User toEntity(String encodePassword) {
        User user = new User();
        user.setUserLoginId(this.loginId);
        user.setUserPassword(encodePassword);
        user.setUserPhoneNum(this.userPhoneNum);
        user.setUserNickName(this.userNickName);
        return user;
    }
}