package com.example.tlover.domain.authority_diary.repository;

import com.example.tlover.domain.authority_diary.entity.AuthorityDiary;
import com.example.tlover.domain.user.entity.User;
import org.apache.http.auth.AUTH;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityDiaryRepository extends JpaRepository<AuthorityDiary, Long> {
    void deleteByDiary_DiaryId(Long diaryId);

    Optional<AuthorityDiary
            > findByAuthorityDiaryId(Long authorityDiaryId);

    Optional<AuthorityDiary
            > findByUserUserId(Long userId);

    Optional<AuthorityDiary
            > findByUserUserIdAndDiaryDiaryId(Long userId, Long diaryId);


    Optional<AuthorityDiary> findByAuthorityDiaryIdAndUser(Long authorityDiaryId, User user);
}
