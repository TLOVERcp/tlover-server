package com.example.tlover.domain.scrap.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@Builder
@ApiModel(description = "유저의 스크랩 생성/삭제 요청 객체")
public class ScrapChangeRequest {

    @NotNull(message = "스크랩 하고자하는 유저의 userId를 입력해주세요.")
    @ApiModelProperty(notes = "userId를 입력해 주세요.")
    private Long userId;

    @NotNull(message = "스크랩 하고자하는 diaryId를 입력해 주세요.")
    @ApiModelProperty(notes = "diaryId를 입력해 주세요.")
    private Long diaryId;
}
