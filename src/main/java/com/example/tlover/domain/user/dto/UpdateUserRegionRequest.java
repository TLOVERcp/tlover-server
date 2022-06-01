package com.example.tlover.domain.user.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "사용자 관심 지역 수정을 위한 요청 객체")
public class UpdateUserRegionRequest {

    @Size(min = 0, max = 3, message = "관심 지역은 0개 이상 3개 이하로 선택해야 합니다.")
    @ApiModelProperty(notes = "사용자 관심 지역을 입력해주세요")
    private String[] userRegions;
}
