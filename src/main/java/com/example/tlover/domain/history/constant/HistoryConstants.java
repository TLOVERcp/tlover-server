package com.example.tlover.domain.history.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class HistoryConstants {
    @Getter
    @RequiredArgsConstructor
    public enum EHistory {
        eComplete("COMPLETE"),
        eFinish("FINISH"),
        eDelete("DELETE");

        private final String value;
    }
}
