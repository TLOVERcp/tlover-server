package com.example.tlover.domain.user.repository;

import com.example.tlover.domain.user.entity.UserThema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserThemaRepository extends JpaRepository<UserThema , Long> {

    List<UserThema> findByUserUserId(Long userUserId);

    List<UserThema> findByThemaThemaId(Long themaThemaId);


}