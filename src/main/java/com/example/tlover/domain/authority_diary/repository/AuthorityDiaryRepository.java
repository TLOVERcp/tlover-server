package com.example.tlover.domain.authority_diary.repository;

import com.example.tlover.domain.authority_diary.entity.AuthorityDiary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityDiaryRepository extends JpaRepository<AuthorityDiary, Long> {
    void deleteByDiary_DiaryId(Long diaryId);
}
