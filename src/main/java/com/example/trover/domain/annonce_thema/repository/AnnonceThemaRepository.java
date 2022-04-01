package com.example.trover.domain.annonce_thema.repository;

import com.example.trover.domain.annonce_thema.entity.AnnonceThema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnonceThemaRepository extends JpaRepository<AnnonceThema , Long> {
}
