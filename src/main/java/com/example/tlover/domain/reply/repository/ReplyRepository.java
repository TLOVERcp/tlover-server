package com.example.tlover.domain.reply.repository;

import com.example.tlover.domain.reply.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
