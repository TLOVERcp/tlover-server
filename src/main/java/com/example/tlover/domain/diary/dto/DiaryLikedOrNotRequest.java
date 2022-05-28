package com.example.tlover.domain.diary.dto;

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
@ApiModel(description = "유저의 좋아요 여부 요청 객체")
public class DiaryLikedOrNotRequest {

    @NotNull(message = "좋아요 여부를 확인하고 싶은 diaryId를 입력해 주세요.")
    @ApiModelProperty(notes = "diaryId를 입력해 주세요.")
    private Long diaryId;

}
