package com.example.tlover.domain.scrap.dto;

import com.example.tlover.domain.myfile.entity.MyFile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@ApiModel(description = "다이어리 스크랩 많은 순 조회 위한 객체")
public class DiaryInquiryByScrapRankingResponse {
    private Long diaryId;
    private String diaryTitle;
    private String  diaryPublicStatus;
    private String diaryStatus;
    private String diaryContext;
    private String diaryStartDate;
    private String diaryWriteDate;
    private String diaryEndDate;
    private String diaryView;
    private String myFileKey;
    private Long scrapCount;

    @QueryProjection
    public DiaryInquiryByScrapRankingResponse(Long diaryId, String diaryTitle, String diaryPublicStatus, String diaryStatus, String diaryContext,
                                              String diaryStartDate, String diaryWriteDate, String diaryEndDate, String diaryView, String myFileKey, Long scrapCount) {
        this.diaryId = diaryId;
        this.diaryTitle = diaryTitle;
        this.diaryPublicStatus = diaryPublicStatus;
        this.diaryStatus = diaryStatus;
        this.diaryContext = diaryContext;
        this.diaryStartDate = diaryStartDate;
        this.diaryWriteDate = diaryWriteDate;
        this.diaryEndDate = diaryEndDate;
        this.diaryView = diaryView;
        this.scrapCount = scrapCount;
        this.myFileKey = myFileKey;
    }
}
