package com.example.tlover.domain.scrap.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
@ApiModel(description = "유저의 스크랩 생성/삭제 응답 객체")
public class ScrapChangeResponse {

    private Long scrapId;
    private boolean isCreated;

    public static ScrapChangeResponse from(Long scrapId, boolean isCreated) {
        return ScrapChangeResponse.builder()
                .scrapId(scrapId)
                .isCreated(isCreated)
                .build();
    }
}
