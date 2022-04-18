package com.example.tlover.domain.reply.service;

import com.example.tlover.domain.reply.dto.ReplyRequest;
import com.example.tlover.domain.reply.dto.ReplyUpdateRequest;
import com.example.tlover.domain.reply.entity.Reply;

import java.util.List;

public interface ReplyService {

    List<Reply> getReplyList(Long DiaryId);

    void insertReply(ReplyRequest replyRequest, String loginId);

    void updateReply(ReplyUpdateRequest replyUpdateRequest, String loginId);

    void deleteReply(ReplyUpdateRequest replyUpdateRequest, String loginId);
}
