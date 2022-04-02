package com.example.tlover.domain.diary.repository;

import com.example.tlover.domain.diary.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnonceRepository extends JpaRepository<Diary, Long> {
}
