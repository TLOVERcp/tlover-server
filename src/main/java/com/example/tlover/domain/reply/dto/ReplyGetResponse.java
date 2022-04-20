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
@ApiModel(description = "댓글 조회를 위한 응답 객체")
public class ReplyGetResponse {

    private Long replyId;
    private Long diaryId;
    private String replyContext;
    private String replyState;
    private String writerNickname;
    private LocalDateTime writeTime;

    public static ReplyGetResponse from(Reply reply) {
        return ReplyGetResponse.builder()
                .replyId(reply.getReplyId())
                .diaryId(reply.getDiary().getDiaryId())
                .replyContext(reply.getReplyContext())
                .replyState(reply.getReplyState())
                .writerNickname(reply.getUser().getUserNickName())
                .writeTime(reply.getReplyTime())
                .build();
    }

}
