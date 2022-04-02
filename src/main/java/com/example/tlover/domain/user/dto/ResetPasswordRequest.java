package com.example.tlover.domain.user.dto;

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
@ApiModel(description = "비밀번호 재설정을 위한 요청 객체")
public class ResetPasswordRequest {

    @NotBlank(message = "회원의 이전 비밀번호를 입력해주세요.")
    @Size(min = 7, max = 18, message = "로그인 Id는 크기가 7에서 18사이여야 합니다.")
    @ApiModelProperty(notes = "회원의 이전 비밀번호를 입력해주세요.")
    private String beforePassword;

    @NotBlank(message = "변경할 비밀번호를 입력해 주세요.")
    @Size(min = 7, max = 20, message = "패스워드는 7글자 이상 20글자 이하여야 합니다.")
    @ApiModelProperty(notes = "변경할 비밀번호를 입력해 주세요.")
    private String afterPassword;
}