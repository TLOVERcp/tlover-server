package com.example.tlover.domain.scrap.repository;

import com.example.tlover.domain.scrap.entity.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
}
