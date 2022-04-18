package com.example.tlover.domain.scrap.dto;

import com.example.tlover.domain.scrap.entity.Scrap;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
@ApiModel(description = "유저의 스크랩 수 응답 객체")
public class ScrapCountResponse {

    private Long scrapCount;

    public static ScrapCountResponse from(Long count) {
        return ScrapCountResponse.builder()
                .scrapCount(count)
                .build();
    }
}
