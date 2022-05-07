package com.example.tlover.domain.user_thema.repository;

import com.example.tlover.domain.thema.entity.Thema;
import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user_thema.entitiy.UserThema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserThemaRepository extends JpaRepository<UserThema , Long> {

    List<UserThema> findByUserUserId(Long userUserId);

    List<UserThema> findByThemaThemaId(Long themaThemaId);


}