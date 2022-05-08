package com.example.tlover.domain.diary.dto;


import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.diary_region.entity.DiaryRegion;
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
@ApiModel(description = "내가 쓴 다이어리 목록을 조회하기 위한 응답 객체")
public class MyDiaryListResponse {

    private Long diaryId;
    private String diaryTitle;
    private String diaryStatus;
    private String diaryContext;
    private String diaryStartDate;
    private String diaryWriteDate;
    private String diaryEndDate;
    private List<String> regionNames;
    private List<String> themaNames;

    public static MyDiaryListResponse from(Diary diary, List<String> diaryRegionNames, List<String> diaryThemaNames){
        return MyDiaryListResponse.builder()
                .diaryId(diary.getDiaryId())
                .diaryTitle(diary.getDiaryTitle())
                .diaryStatus(diary.getDiaryStatus())
                .diaryContext(diary.getDiaryContext())
                .diaryStartDate(diary.getDiaryStartDate())
                .diaryWriteDate(diary.getDiaryWriteDate())
                .diaryEndDate(diary.getDiaryEndDate())
                .regionNames(diaryRegionNames)
                .themaNames(diaryThemaNames)
                .build();
    }
}
