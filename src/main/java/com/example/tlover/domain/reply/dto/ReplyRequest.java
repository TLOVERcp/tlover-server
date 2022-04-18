package com.example.tlover.domain.reply.dto;

import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.reply.entity.Reply;
import com.example.tlover.domain.user.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "댓글을 위한 요청 객체")
public class ReplyRequest {

    @NotBlank(message = "댓글 내용을 입력해주세요.")
    @ApiModelProperty(notes = "댓글 내용을 입력해주세요.")
    private String replyContext;

    @NotNull(message = "댓글 내용을 입력해주세요.")
    @ApiModelProperty(notes = "댓글 내용을 입력해주세요.")
    private Long diaryId;

    private String replyState;

    public Reply toEntity(Diary diary, User user) {
        Reply reply = new Reply();
        reply.setReplyContext(this.replyContext);
        reply.setDiary(diary);
        reply.setUser(user);
        reply.setReplyTime(LocalDateTime.now());
        reply.setReplyState(this.replyState);
        return reply;
    }

}