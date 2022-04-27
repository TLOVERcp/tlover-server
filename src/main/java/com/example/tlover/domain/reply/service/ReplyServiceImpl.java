package com.example.tlover.domain.reply.service;

import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.diary.exception.NotFoundDiaryException;
import com.example.tlover.domain.diary.repository.DiaryRepository;
import com.example.tlover.domain.reply.dto.ReplyDeleteRequest;
import com.example.tlover.domain.reply.dto.ReplyInsertRequest;
import com.example.tlover.domain.reply.dto.ReplyGetResponse;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{

    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;
    private final DiaryRepository diaryRepository;

    /**
     * 댓글 조회
     * @param diaryId
     * @return
     * @author 윤여찬
     */
    @Override
    public List<ReplyGetResponse> getReplyList(Long diaryId) {

        List<ReplyGetResponse> responseList = new ArrayList<>();
        Diary diary = diaryRepository.findByDiaryId(diaryId);

        if (diary == null) throw new NotFoundDiaryException();

        Optional<List<Reply>> replyList = replyRepository.findByDiary(diary);

        if (!replyList.isEmpty()) {

            for (Reply reply : replyList.get()) {
                reply.setDiary(diary);

                if (reply.getReplyState().equals("ACTIVE")) {
                    responseList.add(ReplyGetResponse.from(reply));
                }

            }
        }


        return responseList;
    }

    /**
     * 댓글 등록
     * @param replyInsertRequest, loginId
     * @return
     * @author 윤여찬
     */
    @Override
    public void insertReply(ReplyInsertRequest replyInsertRequest, String loginId) {

        Diary diary = new Diary();
        diary.setDiaryId(replyInsertRequest.getDiaryId());

        User user = userRepository.findByUserLoginId(loginId).get();
        Reply reply = replyInsertRequest.toEntity(diary, user);
        reply.setReplyState("ACTIVE");
        this.checkReply(reply, loginId);

        replyRepository.save(reply);
    }

    /**
     * 댓글 수정
     * @param replyUpdateRequest, loginId
     * @return
     * @author 윤여찬
     */
    @Override
    @Transactional
    public void updateReply(ReplyUpdateRequest replyUpdateRequest, String loginId) {

        Reply reply = replyRepository.findByReplyId(replyUpdateRequest.getReplyId()).orElseThrow(NotFindReplyException::new);
        this.checkReply(reply, loginId);

        reply.setReplyContext(replyUpdateRequest.getReplyContext());
    }

    /**
     * 댓글 삭제
     * @param replyDeleteRequest, loginId
     * @return
     * @author 윤여찬
     */
    @Override
    @Transactional
    public void deleteReply(ReplyDeleteRequest replyDeleteRequest, String loginId) {

        Reply reply = replyRepository.findByReplyId(replyDeleteRequest.getReplyId()).orElseThrow(NotFindReplyException::new);
        this.checkReply(reply, loginId);

        reply.setReplyState("DELETED");
    }

    /**
     * 댓글 확인(Exception 처리)
     * @param reply, loginId
     * @return
     * @author 윤여찬
     */
    public void checkReply(Reply reply, String loginId) {
        User user = userRepository.findByUserLoginId(loginId).get();
        Diary diary = diaryRepository.findByDiaryId(reply.getDiary().getDiaryId());

        if (reply.getUser().getUserId() != user.getUserId()) throw new NotEqualUserIdException();

        if (diary == null) throw new NotFoundDiaryException();
    }

}
