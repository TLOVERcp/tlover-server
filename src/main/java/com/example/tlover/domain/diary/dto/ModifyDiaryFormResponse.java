package com.example.tlover.domain.diary.dto;

import com.example.tlover.domain.diary.entity.Diary;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "다이어리 수정폼 응답 객체")
public class ModifyDiaryFormResponse {

    private Long diaryId;
    private String diaryTitle;
    private String diaryContext;
    private String diaryStatus;
    private String diaryView;
    private String diaryStartDate;
    private String diaryEndDate;
    private String diaryWriteDate;
    private List<String> diaryThemas;
    private String diaryRegionDetail;
    private int totalCost;
    private int diaryPlanDays;
    private Map<Long ,String> myFileSet;

    public static ModifyDiaryFormResponse from(Diary diary , List<String> diaryThemas ,  Map<Long ,String > myFileSet) {
        return ModifyDiaryFormResponse.builder()
                .diaryId(diary.getDiaryId())
                .diaryTitle(diary.getDiaryTitle())
                .diaryContext(diary.getDiaryContext())
                .diaryStatus(diary.getDiaryStatus())
                .diaryStartDate(diary.getDiaryStartDate())
                .diaryEndDate(diary.getDiaryEndDate())
                .diaryWriteDate(diary.getDiaryWriteDate())
                .diaryRegionDetail(diary.getDiaryRegionDetail())
                .totalCost(diary.getTotalCost())
                .diaryThemas(diaryThemas)
                .myFileSet(myFileSet)
                .diaryPlanDays(diary.getDiaryPlanDays()).build();
    }



}
