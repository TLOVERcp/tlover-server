package com.example.tlover.domain.diary.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "다이어리와 관련된 계획 요청 객체")
public class DiaryPlanResponse {

    private Long planId;

    public static DiaryPlanResponse from(Long planId) {
        return DiaryPlanResponse.builder()
                .planId(planId)
                .build();
    }



}
