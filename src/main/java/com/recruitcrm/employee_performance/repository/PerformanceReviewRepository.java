package com.recruitcrm.employee_performance.repository;

import com.recruitcrm.employee_performance.entity.PerformanceReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PerformanceReviewRepository extends JpaRepository<PerformanceReview, Long> {
    List<PerformanceReview> findTop3ByEmployeeIdOrderByReviewDateDesc(Long employeeId);
    List<PerformanceReview> findByEmployeeIdOrderByReviewDateDesc(Long employeeId);
    List<PerformanceReview> findByReviewDateAndScoreGreaterThanEqual(LocalDate reviewDate, Double minScore);
}
