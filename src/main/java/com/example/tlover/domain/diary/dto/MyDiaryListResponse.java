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
@ApiModel(description = "내가 쓴 다이어리 목록을 조회하기 위한 응답 객체")
public class MyDiaryListResponse {

    private Long diaryId;
    private Long planId;
    private String diaryTitle;
    private String diaryStatus;
    private String diaryContext;
    private String diaryStartDate;
    private String diaryWriteDate;
    private String diaryEndDate;
    private String regionNames;
    private List<String> themaNames;
    private List<String> myFileKeys;

    public static MyDiaryListResponse from(Diary diary, String diaryRegionNames, List<String> diaryThemaNames){
        List<MyFile> myFiles = diary.getMyFiles();
        List<String> myFileKeys = new ArrayList<>();
        for (MyFile myFile : myFiles) myFileKeys.add(myFile.getFileKey());


        return MyDiaryListResponse.builder()
                .diaryId(diary.getDiaryId())
                .planId(diary.getPlan().getPlanId())
                .diaryTitle(diary.getDiaryTitle())
                .diaryStatus(diary.getDiaryStatus())
//                .diaryContext(diary.getDiaryContext())
                .diaryStartDate(diary.getDiaryStartDate())
                .diaryWriteDate(diary.getDiaryWriteDate())
                .diaryEndDate(diary.getDiaryEndDate())
                .regionNames(diaryRegionNames)
                .themaNames(diaryThemaNames)
                .myFileKeys(myFileKeys)
                .build();
    }
}
