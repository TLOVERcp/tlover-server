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
@ApiModel(description = "다이어리 요청을 보낸 호스트가 요청들을 확인하기위한 객체")
public class AuthorityDiaryListForHostResponse {

    private Long authorityId;

    private Long diaryId;

    private Long userId;

    private String status;

    public static AuthorityDiaryListForHostResponse from (AuthorityDiary authorityDiary) {
        return AuthorityDiaryListForHostResponse.builder()
                .authorityId(authorityDiary.getAuthorityDiaryId())
                .diaryId(authorityDiary.getDiary().getDiaryId())
                .userId(authorityDiary.getUser().getUserId())
                .status(authorityDiary.getAuthorityDiaryStatus()).build();
    }
}
