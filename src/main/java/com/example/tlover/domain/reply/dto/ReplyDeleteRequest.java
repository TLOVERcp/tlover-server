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
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "댓글을 위한 요청 객체")
public class ReplyDeleteRequest {

    @NotNull(message = "댓글의 id를 입력해주세요.")
    @ApiModelProperty(notes = "댓글의 id를 입력해주세요.")
    private Long replyId;

}