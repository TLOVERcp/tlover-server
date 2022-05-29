package com.example.tlover.domain.diary.dto;

import com.example.tlover.domain.scrap.dto.ScrapOrNotResponse;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
@ApiModel(description = "해당 유저의 해당 다이어리 좋아요 여부 조회 응답 객체")
public class DiaryLikedOrNotResponse {

    private boolean isLiked;

    public static DiaryLikedOrNotResponse from(boolean isLiked) {
        return DiaryLikedOrNotResponse.builder()
                .isLiked(isLiked)
                .build();
    }

}
