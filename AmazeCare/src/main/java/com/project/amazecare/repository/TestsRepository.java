package com.project.amazecare.repository;

import com.project.amazecare.model.RecommendedTests;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestsRepository extends JpaRepository<RecommendedTests, Long> {
}
