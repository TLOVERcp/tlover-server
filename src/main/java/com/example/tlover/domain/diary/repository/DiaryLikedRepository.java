

package com.example.tlover.domain.diary.repository;

import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.diary.entity.DiaryLiked;
import com.example.tlover.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiaryLikedRepository extends JpaRepository<DiaryLiked , Long> {

    Optional<DiaryLiked> findByUserAndDiary(User user , Diary diary);

    Optional<Long> countByDiaryAndIsLiked(Diary diary, boolean b);

    Optional<DiaryLiked> findByUserAndDiaryAndIsLiked(User user, Diary diary, boolean b);
}
