package com.example.tlover.domain.scrap.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class ScrapConstants {
    @Getter
    @RequiredArgsConstructor
    public enum EScrapResponseMessage{
        eScrapCountSuccess("스크랩 수를 조회했습니다."),
        eCreateScrapMessage("스크랩을 완료했습니다."),
        eDeleteScrapMessage("스크랩을 취소했습니다.");
        private final String message;
    }

}

