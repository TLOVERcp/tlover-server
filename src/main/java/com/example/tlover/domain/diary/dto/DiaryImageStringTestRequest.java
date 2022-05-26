package com.example.tlover.domain.diary.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "다이어리 사진 String 테스트 요청")
public class DiaryImageStringTestRequest {


    @ApiModelProperty(notes = "여행시에 찍었던 사진들")
//    private List<MultipartFile> diaryImages;
    private List<String> diaryImages;
}
