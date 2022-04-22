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
@ApiModel(description = "사용자 관심지역 조회를 위한 응답 객체")
public class UserRegionResponse {
    private String regionName;
    private Long userId;
    private String userNickName;

    public static UserRegionResponse from(UserRegion userRegion) {

        return UserRegionResponse.builder()
                .userId(userRegion.getUser().getUserId())
                .userNickName(userRegion.getUser().getUserNickName())
                .regionName(userRegion.getRegion().getRegionName())
                .build();
    }
}
