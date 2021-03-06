package com.example.tlover.domain.authority.repository;

import com.example.tlover.domain.authority.entity.AuthorityDiary;
import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public interface AuthorityDiaryRepository extends JpaRepository<AuthorityDiary, Long> {
    void deleteByDiary_DiaryId(Long diaryId);
    List<AuthorityDiary> findByAuthorityDiaryStatusAndUser(String authorityStatus, User user);

    Optional<AuthorityDiary
            > findByAuthorityDiaryId(Long authorityDiaryId);

    Optional<AuthorityDiary
            > findByUserUserId(Long userId);

    Optional<AuthorityDiary> findByUserUserIdAndDiaryDiaryId(Long userId, Long diaryId);

    Optional<AuthorityDiary> findByAuthorityDiaryIdAndUser(Long authorityDiaryId, User user);

    Optional<List<AuthorityDiary>> findByUserAndAuthorityDiaryStatus(User user, String request);

    Optional<AuthorityDiary> findByDiaryAndUser(Diary diary, User user);

    Optional<List<AuthorityDiary>> findByDiaryAndAuthorityDiaryStatusNot(Diary diary, String host);
}
