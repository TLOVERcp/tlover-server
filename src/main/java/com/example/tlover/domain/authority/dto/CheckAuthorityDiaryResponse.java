package com.example.tlover.domain.authority.dto;

import com.example.tlover.domain.authority.entity.AuthorityDiary;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "다이어리 작성/수정 권한 확인하는 객체")
public class CheckAuthorityDiaryResponse {

    private Long AuthorityDiaryId;
    private Long diaryId;
    private String status;

    public static CheckAuthorityDiaryResponse from(AuthorityDiary authorityDiary) {
        return CheckAuthorityDiaryResponse.builder()
                .AuthorityDiaryId(authorityDiary.getAuthorityDiaryId())
                .diaryId(authorityDiary.getDiary().getDiaryId())
                .status(authorityDiary.getAuthorityDiaryStatus()).build();
    }




}
