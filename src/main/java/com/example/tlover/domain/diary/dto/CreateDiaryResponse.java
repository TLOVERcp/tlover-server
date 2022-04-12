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
@ApiModel(description = "다이어리 작성을 위한 응답 객체")
public class CreateDiaryResponse {

    private String message;

}
