package com.example.tlover.domain.authority.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "계획 공유 요청 응답을 위한 응답 객체")
public class UpdateAuthorityPlanResponse {
    private String message;
}
