package com.example.tlover.domain.thema.repository;


import com.example.tlover.domain.thema.entity.Thema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThemaRepository extends JpaRepository<Thema, Long> {
    Thema findByThemaName(String themaName);
}
