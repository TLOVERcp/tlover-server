package com.example.tlover.domain.diary.dto;

import com.example.tlover.domain.diary.entity.Diary;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "다이어리 상태 변환을 위한 객체")
public class UpdateDiaryStatusResponse {

    private Long diaryId;
    private String status;

    public static UpdateDiaryStatusResponse from(Diary diary) {
        return UpdateDiaryStatusResponse.builder()
                        .diaryId(diary.getDiaryId())
                        .status(diary.getDiaryStatus()).build();
    }

}
