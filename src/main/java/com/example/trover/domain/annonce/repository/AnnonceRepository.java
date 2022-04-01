package com.example.trover.domain.annonce.repository;

import com.example.trover.domain.annonce.entity.Annonce;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnonceRepository extends JpaRepository<Annonce, Long> {
}
