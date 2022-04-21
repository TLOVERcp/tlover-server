package com.example.tlover.domain.scrap.repository;

import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.scrap.entity.Scrap;
import com.example.tlover.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long>, ScrapRepositoryCustom {
    Optional<Scrap> findByUserAndDiary(User user, Diary diary);
}
