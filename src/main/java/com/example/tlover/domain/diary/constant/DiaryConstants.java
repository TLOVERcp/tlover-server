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
        EDIT("EDIT"),
        COMPLETE("COMPLETE"),
        DefaultImageKey("4cebbe25-faa1-4490-98e1-6d22f2a54f90"),
        DayPattern("yyyy-MM-dd HH:mm:ss");


        private final String value;
    }




}
