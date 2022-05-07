package com.example.tlover.domain.diary.dto;

import com.example.tlover.domain.thema.entity.Thema;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@ApiModel(description = "다이어리 검색을 위한 객체")
public class SearchResponse {
    private DiarySearchResponse diarySearchResponse;
    private List<Thema> themas;

    // 테마 이름 여러개 어떻게 리턴해야 하는지?


}
