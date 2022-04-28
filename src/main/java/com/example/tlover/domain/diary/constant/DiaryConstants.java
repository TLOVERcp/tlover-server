package com.example.tlover.domain.diary.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class DiaryConstants {

    @Getter
    @AllArgsConstructor

    public enum eDiary {

        HOST("HOST"),
        REQUEST("REQUEST"),
        REJECT("REJECT"),
        ACTIVE("ACTIVE"),
        DELETE("DELETE"),

        AlreadyExistDiaryExceptionMessage("하나의 여행계획에 유저당 하나씩 작성이 가능합니다."),
        NotAuthorityDelete("삭제 권한이 없습니다.");

        private final String value;
    }




}
