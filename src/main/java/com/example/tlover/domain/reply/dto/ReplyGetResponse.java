package com.example.tlover.domain.reply.dto;


import com.example.tlover.domain.reply.entity.Reply;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


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
    private String writeTime;

    @QueryProjection
    public ReplyGetResponse(Long replyId, Long diaryId, String replyContext, String replyState, String writerNickname,
                            LocalDateTime writeTime) {
        this.replyId = replyId;
        this.diaryId = diaryId;
        this.replyContext = replyContext;
        this.replyState = replyState;
        this.writerNickname = writerNickname;
        this.writeTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(writeTime);

    }

    public static ReplyGetResponse from(Reply reply) {
        return ReplyGetResponse.builder()
                .replyId(reply.getReplyId())
                .diaryId(reply.getDiary().getDiaryId())
                .replyContext(reply.getReplyContext())
                .replyState(reply.getReplyState())
                .writerNickname(reply.getUser().getUserNickName())
                .writeTime(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(reply.getReplyTime()))
                .build();
    }

}
