package com.example.tlover.domain.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "비밀번호 찾기을 위한 요청 객체")
public class FindPasswordRequest {

    @NotBlank(message = "회원의 로그인Id를 입력해주세요.")
    @Pattern(regexp = "^[a-z0-9]{6,18}$", message = "로그인 Id는 6~18글자의 영소문자, 숫자만 가능합니다.")
    @ApiModelProperty(notes = "로그인 Id를 입력해 주세요.")
    private String loginId;

    @NotBlank(message = "회원의 변경할 비밀번호를 입력해 주세요.")
    @Pattern(regexp = "^.*(?=^.{8,20}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$",
             message = "비밀번호는 특수문자를 포함한 8~20글자의 영대/소문자, 숫자만 가능합니다.")
    @ApiModelProperty(notes = "회원의 변경할 비밀번호를 입력해 주세요.")
    private String changePassword;

    private String userPhoneNum;

    private String certifiedValue;
}