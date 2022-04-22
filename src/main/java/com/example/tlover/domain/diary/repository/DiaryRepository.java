package com.example.tlover.domain.diary.repository;

import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    Diary findByUserAndDiaryId(User user, Long diaryId);
    List<Diary> findBy();
    Diary findByDiaryId(Long diaryId);

    Optional<Diary> findByUserUserIdAndPlanPlanId(Long userId, Long planId);
}
