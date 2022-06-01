package com.example.tlover.domain.plan.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "계획 지역 선택을 위한 요청 객체")
public class CreatePlanRegionRequest {

    @NotBlank(message = "여행 지역을 선택해주세요.")
    @NotBlank(message = "여행 지역을 선택해주세요.")
    private String regionName;


}
