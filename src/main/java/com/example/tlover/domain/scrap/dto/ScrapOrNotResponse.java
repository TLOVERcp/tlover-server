package com.example.tlover.domain.scrap.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
@ApiModel(description = "해당 유저의 해당 다이어리 스크랩 여부 조회 응답 객체")
public class ScrapOrNotResponse {

    boolean isScraped;

    public static ScrapOrNotResponse from(boolean isScraped) {
        return ScrapOrNotResponse.builder()
                .isScraped(isScraped)
                .build();
    }
}
