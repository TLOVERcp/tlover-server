package com.example.tlover.domain.scrap.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "해당 유저의 해당 다이어리 스크랩 여부 조회 요청 객체")
public class ScrapOrNotRequest {

    @NotNull(message = "diaryId를 입력해 주세요.")
    @ApiModelProperty(notes = "diaryId를 입력해 주세요.")
    private Long diaryId;
}
