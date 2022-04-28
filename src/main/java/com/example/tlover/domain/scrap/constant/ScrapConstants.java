package com.example.tlover.domain.scrap.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class ScrapConstants {
    @Getter
    @RequiredArgsConstructor
    public enum EScrapResponseMessage{
        eScrapCountSuccessMessage("스크랩 수를 조회했습니다."),
        eCreateScrapMessage("스크랩을 완료했습니다."),
        eDeleteScrapMessage("스크랩을 취소했습니다."),
        eScrapOrNotMessage("스크랩 여부를 확인했습니다."),
        eGetDiariesByScrapRankingMessage("모든 다이어리를 스크랩 랭" +
                "" +
                "킹 순으로 조회합니다.");
        private final String message;
    }

}

