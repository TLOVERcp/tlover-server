package com.example.tlover.domain.authority_diary.dto;


import com.example.tlover.domain.authority_diary.entity.AuthorityDiary;
import com.example.tlover.domain.diary.dto.CreateDiaryResponse;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "다이어리 권한 공유을 위한 응답 객체")
public class AuthorityDiaryResponse {

    private Long authorityDiaryId;
    private Long userId;
    private String status;

    public static AuthorityDiaryResponse from(AuthorityDiary authorityDiary) {
        return AuthorityDiaryResponse.builder()
                .authorityDiaryId(authorityDiary.getAuthorityDiaryId())
                .userId(authorityDiary.getUser().getUserId())
                .status(authorityDiary.getAuthorityDiaryStatus())
                .build();
    }


}
