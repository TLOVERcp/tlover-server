package com.example.tlover.domain.diary.dto;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@ApiModel(description = "다이어리 좋아요 많은 순 조회 위한 객체")
public class DiaryInquiryByLikedRankingResponse {

    private Long diaryId;
    private String diaryTitle;
    private String diaryStatus;
    private String diaryStartDate;
    private String diaryWriteDate;
    private String diaryEndDate;
    private String diaryView;
    private Long likedCount;

    @QueryProjection
    public DiaryInquiryByLikedRankingResponse(Long diaryId, String diaryTitle, String diaryStatus,
                                              String diaryStartDate, String diaryWriteDate, String diaryEndDate, String diaryView, Long likedCount) {
        this.diaryId = diaryId;
        this.diaryTitle = diaryTitle;
        this.diaryStatus = diaryStatus;
        this.diaryStartDate = diaryStartDate;
        this.diaryWriteDate = diaryWriteDate;
        this.diaryEndDate = diaryEndDate;
        this.diaryView = diaryView;
        this.likedCount = likedCount;
    }

}
