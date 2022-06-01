package com.example.tlover.domain.diary.dto;

import com.example.tlover.domain.diary.entity.Diary;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "홈 화면의 나와 여행 취향이 닮은 사람들의 조회 요청 객체")
public class DiaryPreferenceResponse {
    private Long diaryId;
    private String diaryTitle;
    private String startDate;

    private List<String> regionName;

    private List<String> image;

    private String userNickName;

    public static DiaryPreferenceResponse from(Diary diary, List<String> regionName, List<String> image){
        return DiaryPreferenceResponse.builder()
                .diaryId(diary.getDiaryId())
                .diaryTitle(diary.getDiaryTitle())
                .startDate(diary.getDiaryStartDate().toString())
                .regionName(regionName)
                .image(image)
                .userNickName(diary.getUser().getUserNickName())
                .build();
    }


}
