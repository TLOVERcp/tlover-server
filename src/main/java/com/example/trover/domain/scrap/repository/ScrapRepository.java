package com.example.trover.domain.scrap.repository;

import com.example.trover.domain.scrap.entity.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
}
