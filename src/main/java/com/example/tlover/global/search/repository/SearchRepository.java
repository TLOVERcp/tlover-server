package com.example.tlover.global.search.repository;

import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.diary.repository.DiaryRepositoryCustom;
import com.example.tlover.domain.diary_thema.entity.DiaryThema;
import com.example.tlover.domain.plan.entity.Plan;
import com.example.tlover.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SearchRepository extends JpaRepository<Diary, Long> , SearchRepositoryCustom {

}
