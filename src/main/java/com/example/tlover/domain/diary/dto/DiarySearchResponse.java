package com.example.tlover.domain.diary.dto;

import com.example.tlover.domain.thema.entity.Thema;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@ApiModel(description = "다이어리 검색을 위한 객체")
public class DiarySearchResponse {
    private Long diaryId;
    private String diaryTitle;
    private String  diaryPublicStatus;
    private String diaryStatus;
    private String diaryContext;
    private String diaryStartDate;
    private String diaryWriteDate;
    private String diaryEndDate;
    private String diaryView;
    private List<String> themaNames;


    @QueryProjection
    public DiarySearchResponse(Long diaryId, String diaryTitle, String diaryPublicStatus, String diaryStatus, String diaryContext,
                               String diaryStartDate, String diaryWriteDate, String diaryEndDate, String diaryView) {
        this.diaryId = diaryId;
        this.diaryTitle = diaryTitle;
        this.diaryPublicStatus = diaryPublicStatus;
        this.diaryStatus = diaryStatus;
        this.diaryContext = diaryContext;
        this.diaryStartDate = diaryStartDate;
        this.diaryWriteDate = diaryWriteDate;
        this.diaryEndDate = diaryEndDate;
        this.diaryView = diaryView;
    }

    public void setThemaNames(List<String> themaNames) {
        this.themaNames = themaNames;
    }
}
