package com.example.tlover.domain.reply.service;

import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.diary.repository.DiaryRepository;
import com.example.tlover.domain.reply.dto.ReplyRequest;
import com.example.tlover.domain.reply.dto.ReplyUpdateRequest;
import com.example.tlover.domain.reply.entity.Reply;
import com.example.tlover.domain.reply.exception.NotEqualUserIdException;
import com.example.tlover.domain.reply.exception.NotFindReplyException;
import com.example.tlover.domain.reply.repository.ReplyRepository;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{

    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;

    @Override
    public List<Reply> getReplyList(Long DiaryId) {
        return null;
    }

    @Override
    public void insertReply(ReplyRequest replyRequest, String loginId) {

        Diary diary = new Diary();
        diary.setDiaryId(replyRequest.getDiaryId());

        User user = userRepository.findByUserLoginId(loginId).get();

        replyRepository.save(replyRequest.toEntity(diary, user));
    }

    @Override
    @Transactional
    public void updateReply(ReplyUpdateRequest replyUpdateRequest, String loginId) {

        Reply reply = replyRepository.findByReplyId(replyUpdateRequest.getReplyId()).get();
        this.checkReply(reply, loginId);

        reply.setReplyContext(replyUpdateRequest.getReplyContext());
        reply.setReplyState(reply.getReplyState());

    }

    @Override
    public void deleteReply(ReplyUpdateRequest replyUpdateRequest, String loginId) {

        Reply reply = replyRepository.findByReplyId(replyUpdateRequest.getReplyId()).get();
        this.checkReply(reply, loginId);

        replyRepository.delete(reply);
    }

    public void checkReply(Reply reply, String loginId) {
        User user = userRepository.findByUserLoginId(loginId).get();
        if (reply == null) throw new NotFindReplyException();

        if (reply.getUser().getUserId() != user.getUserId()) throw new NotEqualUserIdException();
    }

}
