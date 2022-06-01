package com.example.tlover.domain.authority.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class AuthorityPlanConstant {

    @Getter
    @AllArgsConstructor
    public enum EAuthorityPlanState{
        HOST("HOST"),
        REQUEST("REQUEST"),
        REJECT("REJECT"),
        ACCEPT("ACCEPT");

        private final String value;
    }
}

