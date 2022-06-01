package com.example.tlover.domain.search.dto;

import com.example.tlover.global.config.constants.Constants;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@ApiModel(description = "다이어리 검색을 위한 객체")
public class DiarySearchResponse {
    private Long diaryId;
    private String diaryTitle;
    private String diaryStatus;
    private String diaryStartDate;
    private String diaryWriteDate;
    private String diaryEndDate;
    private String diaryImg = Constants.DEFAULT_IMAGE;
    private int totalCost;
    private boolean isScraped;
    private boolean isLiked;
    private List<String> themaNames;
    private List<String> regionNames;


    @QueryProjection
    public DiarySearchResponse(Long diaryId, String diaryTitle, String diaryStatus,
                               String diaryStartDate, String diaryWriteDate, String diaryEndDate, int totalCost) {
        this.diaryId = diaryId;
        this.diaryTitle = diaryTitle;
        this.diaryStatus = diaryStatus;
        this.diaryStartDate = diaryStartDate;
        this.diaryWriteDate = diaryWriteDate;
        this.diaryEndDate = diaryEndDate;
        this.totalCost = totalCost;
    }
}
