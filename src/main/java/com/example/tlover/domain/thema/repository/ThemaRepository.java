package com.example.tlover.domain.thema.repository;


import com.example.tlover.domain.thema.entity.Thema;
import com.example.tlover.domain.user_thema.entitiy.UserThema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ThemaRepository extends JpaRepository<Thema, Long> {

    Optional<Thema> findByThemaId(Long themaId);

    Optional<Thema> findByThemaName(String themaName);

    Thema findByThemaName(String themaName);
}
