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
@ApiModel(description = "다이어리 좋아요 갯수 응답 객체")
public class DiaryLikedViewsResponse {

    private Long diaryId;
    private Long diaryLikedViews;

    public static DiaryLikedViewsResponse from(Long diaryId , Long diaryLikedViews) {
        return DiaryLikedViewsResponse.builder()
                .diaryId(diaryId)
                .diaryLikedViews(diaryLikedViews)
                .build();
    }

}
