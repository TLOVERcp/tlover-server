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
@ApiModel(description = "회원 탈퇴를 위한 요청 객체")
public class WithdrawUserRequest {

    @NotBlank(message = "회원의 비밀번호를 입력해 주세요.")
    @ApiModelProperty(notes = "회원의 비밀번호를 입력해 주세요.")
    private String password;
}