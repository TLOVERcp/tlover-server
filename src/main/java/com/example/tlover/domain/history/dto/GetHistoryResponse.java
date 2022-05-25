package com.example.tlover.domain.history.dto;

import com.example.tlover.domain.history.entity.History;
import com.example.tlover.domain.myfile.entity.MyFile;
import com.example.tlover.domain.user.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "방문 기록 조회를 위한 응답 객체")
public class GetHistoryResponse {
    private String historyDate;
    private List<String> myFileKeys;
    private String diaryTitle;
    private String regionDetail;
    private int totalCost;
    private String diaryStartDate;
    private String diaryEndDate;
    private boolean isLiked;
    private boolean isScraped;


    public static GetHistoryResponse from(History history, boolean isScraped, boolean isLiked) {
        List<MyFile> myFiles = history.getDiary().getMyFiles();
        List<String> myFileKeys = new ArrayList<>();
        for (MyFile myFile : myFiles) myFileKeys.add(myFile.getFileKey());

        return GetHistoryResponse.builder()
                .myFileKeys(myFileKeys)
                .diaryTitle(history.getDiary().getDiaryTitle())
                .regionDetail(history.getDiary().getDiaryRegionDetail())
                .totalCost(history.getDiary().getTotalCost())
                .diaryStartDate(history.getDiary().getDiaryStartDate())
                .diaryEndDate(history.getDiary().getDiaryEndDate())
                .isScraped(isScraped)
                .isLiked(isLiked)
                .historyDate(history.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }
}
