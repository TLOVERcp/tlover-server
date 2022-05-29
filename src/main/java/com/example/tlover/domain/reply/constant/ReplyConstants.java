package com.example.tlover.domain.reply.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class ReplyConstants {

    @Getter
    @AllArgsConstructor

    public enum eReply {

        eACTIVE("ACTIVE"),
        eDELETE("DELETED");

        private final String value;
    }

}
