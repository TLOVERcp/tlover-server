package com.example.tlover.domain.reply.service;

import com.example.tlover.domain.reply.dto.ReplyDeleteRequest;
import com.example.tlover.domain.reply.dto.ReplyInsertRequest;
import com.example.tlover.domain.reply.dto.ReplyGetResponse;
import com.example.tlover.domain.reply.dto.ReplyUpdateRequest;

import java.util.List;

public interface ReplyService {

    List<ReplyGetResponse> getReplyList(Long DiaryId);

    void insertReply(ReplyInsertRequest replyInsertRequest, String loginId);

    void updateReply(ReplyUpdateRequest replyUpdateRequest, String loginId);

    void deleteReply(ReplyDeleteRequest replyDeleteRequest, String loginId);
}
