package com.example.tlover.domain.reply.repository;

import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.reply.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long>, ReplyRepositoryCustom {

    Optional<Reply> findByReplyId(Long replyId);

    List<Reply> findByUserUserId(Long userUserId);
}
