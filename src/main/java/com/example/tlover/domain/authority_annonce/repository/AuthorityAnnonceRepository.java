package com.example.tlover.domain.authority_annonce.repository;

import com.example.tlover.domain.authority_annonce.entity.AuthorityAnnonce;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityAnnonceRepository extends JpaRepository<AuthorityAnnonce, Long> {
}
