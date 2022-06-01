package com.example.tlover.domain.search.repository;

import com.example.tlover.domain.diary.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchRepository extends JpaRepository<Diary, Long> , SearchRepositoryCustom {

}
