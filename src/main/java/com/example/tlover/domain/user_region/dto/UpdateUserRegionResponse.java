package com.example.tlover.domain.user_region.dto;

import com.example.tlover.domain.user_region.entity.UserRegion;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "사용자 관심지역 수정을 위한 응답 객체")
public class UpdateUserRegionResponse {

    String message;

    public static UpdateUserRegionResponse from(UserRegion userRegion) {

        return UpdateUserRegionResponse.builder()
                .message(userRegion.getUser().getUserNickName()+"의 관심지역 수정 성공")
                .build();
    }
}
