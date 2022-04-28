package com.example.tlover.domain.diary.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
@ApiModel(description = "유저의 좋아요 생성/삭제 응답객체")
public class DiaryLikedChangeResponse {

    private Long diaryIikedId;
    private boolean isLiked;

    public static DiaryLikedChangeResponse from(Long diaryIikedId , boolean isLiked){
       return DiaryLikedChangeResponse.builder()
                .diaryIikedId(diaryIikedId)
                .isLiked(isLiked)
                .build();
    }

}
