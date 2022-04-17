package com.example.tlover.domain.user_region.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "사용자 관심 지역 수정을 위한 요청 객체")
public class UpdateUserRegionRequest {

    @ApiModelProperty(notes = "사용자 관심 지역을 입력해주세요")
    private String[] userRegions;
}
