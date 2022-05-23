package com.example.tlover.domain.diary.dto;

import com.example.tlover.domain.diary.entity.Diary;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "다이어리 날씨 추전 조회 응답 객체")
public class DiaryWeatherResponse {
    private Long diaryId;
    private String diaryTitle;
    private String startDate;

    private List<String> regionName;

    private List<String> image;

    private String userNickName;

    private String weatherTciGrade;

    public static DiaryWeatherResponse from(Diary diary, List<String> regionName, List<String> image){
        return DiaryWeatherResponse.builder()
                .diaryId(diary.getDiaryId())
                .diaryTitle(diary.getDiaryTitle())
                .startDate(diary.getDiaryStartDate())
                .regionName(regionName)
                .image(image)
                .userNickName(diary.getUser().getUserNickName())
                .weatherTciGrade("매우좋음")
                .build();
    }
}
