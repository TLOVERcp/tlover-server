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
@ApiModel(description = "아이디 찾기를 위한 요청 객체")
public class FindIdRequest {

    private String loginId;

    @NotBlank(message = "회원의 전화번호를 입력해주세요.")
    @Size(min = 10, max = 11, message = "전화번호는 크기가 10에서 11사이여야 합니다.")
    @ApiModelProperty(notes = "회원의 전화번호를 입력해주세요.")
    private String userPhoneNum;

}