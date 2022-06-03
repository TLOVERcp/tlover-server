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
@ApiModel(description = "다이어리 수정폼 응답 객체")
public class ModifyDiaryResponse {

    private Long diaryId;
    private String status;
    private String modifiedByUserNickName;

    public static ModifyDiaryResponse from (Diary diary , String userNickName) {
        return ModifyDiaryResponse.builder()
                .diaryId(diary.getDiaryId())
                .status(diary.getDiaryStatus())
                .modifiedByUserNickName(userNickName).build();
    }


}
