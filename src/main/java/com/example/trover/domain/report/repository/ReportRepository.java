package com.example.trover.domain.report.repository;

import com.example.trover.domain.report.entity.Report;
import com.example.trover.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
