package com.example.tlover.domain.reply.dto;


import com.example.tlover.domain.reply.entity.Reply;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "댓글을 위한 응답 객체")
public class ReplyResponse {

    private String message;

    public static ReplyResponse from(String message) {
        return ReplyResponse.builder()
                .message(message)
                .build();
    }

}
