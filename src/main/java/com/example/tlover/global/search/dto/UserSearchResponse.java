package com.example.tlover.global.search.dto;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@ApiModel(description = "사용자 검색을 위한 객체")
public class UserSearchResponse {
    private Long userId;
    private String userNickName;


    @QueryProjection
    public UserSearchResponse(Long userId, String userNickName) {
        this.userId = userId;
        this.userNickName = userNickName;
    }
}
