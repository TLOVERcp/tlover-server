package com.example.tlover.domain.history.repository;

import com.example.tlover.domain.history.entity.History;
import com.example.tlover.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface HistoryRepository extends JpaRepository<History, Long> {
    Optional<History> findByDiary_DiaryIdAndUser_UserId(Long diaryId, Long userId);
    List<History> findByUser(User user);
    @Transactional
    void deleteByUser(User user);
    @Transactional
    void deleteByUserAndAndHistoryId(User user, Long historyId);
}
