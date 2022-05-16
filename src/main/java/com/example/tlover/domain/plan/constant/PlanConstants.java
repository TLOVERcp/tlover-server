package com.example.tlover.domain.plan.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class PlanConstants {
    @Getter
    @AllArgsConstructor
    public enum EPlanStatus{
        ACTIVE("ACTIVE"),
        FINISH("FINISH"),
        EDITING("EDITING"),
        DELETE("DELETE");

        private final String value;

    }
}
