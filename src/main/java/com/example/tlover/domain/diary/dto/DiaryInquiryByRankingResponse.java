package com.example.tlover.domain.diary.dto;

import com.example.tlover.domain.diary.entity.Diary;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "다이어리 내림차순 조회 위한 객체")
public class DiaryInquiryByRankingResponse {

    private Long diaryId;
    private String diaryTitle;
    private String diaryStatus;
    private String diaryContext;
    private String diaryStartDate;
    private String diaryWriteDate;
    private String diaryEndDate;
    private String diaryView;
    private Long liked;

    public static DiaryInquiryByRankingResponse from(Diary diary){
        return DiaryInquiryByRankingResponse.builder()
                .diaryId(diary.getDiaryId())
                .diaryTitle(diary.getDiaryTitle())
                .diaryStartDate(diary.getDiaryStartDate())
                .diaryWriteDate(diary.getDiaryWriteDate())
                .diaryEndDate(diary.getDiaryEndDate())
                .diaryView(diary.getDiaryView())
                .build();
    }




}
