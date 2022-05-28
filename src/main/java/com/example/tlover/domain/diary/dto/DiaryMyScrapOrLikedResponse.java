package com.example.tlover.domain.diary.dto;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@ApiModel(description = "내가 좋아요를 누른 다이어리 조회를 위한 객체")
public class DiaryMyScrapOrLikedResponse {

   private Long diaryId;
   private String diaryTitle;
   private String diaryView;
   private String planStartDate;
   private String planEndDate;
   private int totalDay;
   private String planRegionDetail;
   private int expense;

    public DiaryMyScrapOrLikedResponse() {

    }

    @QueryProjection
    public DiaryMyScrapOrLikedResponse(Long diaryId, String diaryTitle, String diaryView, String planStartDate, String planEndDate, int totalDay, String planRegionDetail, int expense) {
        this.diaryId = diaryId;
        this.diaryTitle = diaryTitle;
        this.diaryView = diaryView;
        this.planStartDate = planStartDate;
        this.planEndDate = planEndDate;
        this.totalDay = totalDay;
        this.planRegionDetail = planRegionDetail;
        this.expense = expense;
    }
}
