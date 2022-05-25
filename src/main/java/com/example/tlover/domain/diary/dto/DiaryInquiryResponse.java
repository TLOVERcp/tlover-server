package com.example.tlover.domain.diary.dto;

import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.myfile.entity.MyFile;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "다이어리 조회 위한 객체")
public class DiaryInquiryResponse {

    private Long diaryId;
    private String diaryTitle;
    private String  diaryPublicStatus;
    private String diaryStatus;
    private String diaryContext;
    private String diaryStartDate;
    private String diaryWriteDate;
    private String diaryEndDate;
    private String diaryView;
    private List<String> myFileKeys;
    private Integer diaryConnectionCount;
    private String regionDetail;

  /*  public static DiaryInquiryResponse from(Diary diary){
        return DiaryInquiryResponse.builder()
                .diaryId(diary.getDiaryId())
                .diaryTitle(diary.getDiaryTitle())
                //.diaryPublicStatus(diary.getDiaryPublicStatus())
                //.diaryStatus(diary.getDiaryStatus())
                //.diaryContext(diary.getDiaryContext())
                .diaryStartDate(diary.getDiaryStartDate())
                .diaryWriteDate(diary.getDiaryWriteDate())
                .diaryEndDate(diary.getDiaryEndDate())
                .diaryView(diary.getDiaryView())
                .build();
    }*/

    public static DiaryInquiryResponse from(Diary diary, Integer diaryConnectionCount){
        List<MyFile> myFiles = diary.getMyFiles();
        List<String> myFileKeys = new ArrayList<>();
        for (MyFile myFile : myFiles) myFileKeys.add(myFile.getFileKey());
        return DiaryInquiryResponse.builder()
                .diaryId(diary.getDiaryId())
                .diaryTitle(diary.getDiaryTitle())
                .diaryStatus(diary.getDiaryStatus())
                .diaryContext(diary.getDiaryContext())
                .diaryStartDate(diary.getDiaryStartDate())
                .diaryWriteDate(diary.getDiaryWriteDate())
                .diaryEndDate(diary.getDiaryEndDate())
                .diaryView(diary.getDiaryView())
                .myFileKeys(myFileKeys)
                .diaryConnectionCount(diaryConnectionCount)
                .regionDetail(diary.getDiaryRegionDetail())
                .build();
    }

}
