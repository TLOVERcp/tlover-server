package com.example.tlover.domain.annonce.repository;

import com.example.tlover.domain.annonce.entity.Annonce;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnonceRepository extends JpaRepository<Annonce, Long> {
}
