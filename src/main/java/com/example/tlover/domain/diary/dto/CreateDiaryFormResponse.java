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
@ApiModel(description = "다이어리 작성폼 응답 객체")
public class CreateDiaryFormResponse {

    private String planStartDate;
    private String planEndDate;
    private int totalDay;
    private String planRegionDetail;
    private Long expense;

    public static CreateDiaryFormResponse from (String psd , String ped ,
                                                int totalDay , String prd, Long expense) {
        return CreateDiaryFormResponse.builder()
                .planStartDate(psd)
                .planEndDate(ped)
                .totalDay(totalDay)
                .planRegionDetail(prd)
                .expense(expense).build();
    }


}
