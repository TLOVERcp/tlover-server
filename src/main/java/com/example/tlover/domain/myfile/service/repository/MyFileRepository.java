package com.example.tlover.domain.myfile.service.repository;

import com.example.tlover.domain.diary.entity.Diary;
import com.example.tlover.domain.myfile.entity.MyFile;
import com.example.tlover.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MyFileRepository extends JpaRepository<MyFile, Long>, MyFileRepositoryCustom {

    Optional<List<MyFile>> findByUserAndDiary(User user, Diary diary);

}
