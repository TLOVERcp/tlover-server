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
@ApiModel(description = "다이어리 요청 확인을 위한 객체")
public class AuthorityDiaryListResponse {

    private Long authorityDiaryId;
    private Long diaryId;

    public static AuthorityDiaryListResponse from(AuthorityDiary authorityDiary) {
       return AuthorityDiaryListResponse.builder()
               .authorityDiaryId(authorityDiary.getAuthorityDiaryId())
               .diaryId(authorityDiary.getDiary().getDiaryId()).build();
    }


}
