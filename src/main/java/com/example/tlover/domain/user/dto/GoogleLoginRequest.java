package com.example.tlover.domain.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "구글 로그인을 위한 요청 객체")
public class GoogleLoginRequest {

    @NotBlank(message = "구글의 idToken 입력해 주세요.")
    @ApiModelProperty(notes = "구글의 idToken을 입력해 주세요.")
    private String idToken;

}
