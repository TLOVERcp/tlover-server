package com.example.tlover.domain.diary.controller;

import com.example.tlover.domain.diary.dto.CreateDiaryResponse;
import io.swagger.annotations.ApiModel;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "다이어리 작성 응답 객체")
public class TestDiaryEntity {

    private String Id;
    private String fileName;

    public static TestDiaryEntity from(String id , String fileName) {
        return TestDiaryEntity.builder()
                .Id(id)
                .fileName(fileName)
                .build();
    }


}
