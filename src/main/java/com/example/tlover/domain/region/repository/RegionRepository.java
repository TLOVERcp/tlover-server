package com.example.tlover.domain.region.repository;

import com.example.tlover.domain.region.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, Long> {
   Optional<Region> findByRegionName(String regionName);
}
