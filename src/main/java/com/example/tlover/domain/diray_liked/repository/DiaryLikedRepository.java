

package com.example.tlover.domain.diray_liked.repository;

import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.diray_liked.entity.DiaryLiked;
import com.example.tlover.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface DiaryLikedRepository extends JpaRepository<DiaryLiked , Long> {

    Optional<DiaryLiked> findByUserAndDiary(User user , Diary diary);

    Optional<Long> countByDiaryAndIsLiked(Diary diary, boolean b);

    Optional<DiaryLiked> findByUserAndDiaryAndIsLiked(User user, Diary diary, boolean b);
}
