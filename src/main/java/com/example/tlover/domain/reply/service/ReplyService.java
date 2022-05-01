package com.example.tlover.domain.reply.service;

import com.example.tlover.domain.reply.dto.ReplyDeleteRequest;
import com.example.tlover.domain.reply.dto.ReplyInsertRequest;
import com.example.tlover.domain.reply.dto.ReplyGetResponse;
import com.example.tlover.domain.reply.dto.ReplyUpdateRequest;
import com.example.tlover.global.dto.PaginationDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReplyService {

    PaginationDto<List<ReplyGetResponse>> getReplyList(Long DiaryId, Pageable pageable);

    void insertReply(ReplyInsertRequest replyInsertRequest, String loginId);

    void updateReply(ReplyUpdateRequest replyUpdateRequest, String loginId);

    void deleteReply(ReplyDeleteRequest replyDeleteRequest, String loginId);
}
