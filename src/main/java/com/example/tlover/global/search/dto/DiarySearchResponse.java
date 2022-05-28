package com.example.tlover.global.search.dto;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

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

    @Value("${default-image}")
    private String diaryImg;
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
