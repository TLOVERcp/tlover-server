package com.example.tlover.global.sms.dto;

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
public class SmsSendRequest {

    @NotBlank(message = "전화번호를 입력해 주세요.")
    @Size(min = 11, max = 11, message = "전화번호를 확인하여 주세요.")
    @ApiModelProperty(notes = "전화번호를 입력해 주세요.")
    private String phoneNum;

    public static SmsSendRequest from(User user) {
        return SmsSendRequest.builder()
                .phoneNum(user.getUserPhoneNum())
                .build();
    }
}
