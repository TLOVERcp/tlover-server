package com.example.tlover.domain.reply.repository;

import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.reply.dto.ReplyGetResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ReplyRepositoryCustom {

    Page<ReplyGetResponse> findByDiaryCustom(Diary diary, Pageable pageable);
}
