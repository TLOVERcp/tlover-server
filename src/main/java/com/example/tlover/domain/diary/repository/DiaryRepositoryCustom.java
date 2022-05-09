package com.example.tlover.domain.diary.repository;

import com.example.tlover.domain.diary.dto.DiarySearchResponse;
import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.reply.dto.ReplyGetResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface DiaryRepositoryCustom {

    Page<DiarySearchResponse> findByKeywordCustom(String keyword, Pageable pageable);

    Page<DiarySearchResponse> findByThemaKewordCustom(String keyword, Pageable pageable);

    List<String> findThemaNamesByDiaryId(Long diaryId);

    List<String> findRegionNamesByDiaryId(Long diaryId);
}
