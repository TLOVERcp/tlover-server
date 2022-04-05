package com.example.tlover.domain.user.dto;

import com.example.tlover.domain.user.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "아이디 중복 확인을 위한 요청 객체")
public class DuplicateRequest {

    @NotBlank(message = "회원의 로그인Id를 입력해주세요.")
    @Size(min = 5, max = 18, message = "로그인 Id는 크기가 5에서 18사이여야 합니다.")
    @ApiModelProperty(notes = "로그인 Id를 입력해 주세요.")
    private String loginId;

    public static DuplicateRequest from(User user) {
        return DuplicateRequest.builder()
                .loginId(user.getUserLoginId())
                .build();
    }

}