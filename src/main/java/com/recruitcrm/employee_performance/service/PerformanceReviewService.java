package com.recruitcrm.employee_performance.service;

import com.recruitcrm.employee_performance.dto.PerformanceReviewDto;

import java.util.List;

public interface PerformanceReviewService {

    List<PerformanceReviewDto> getAllReviews();

    List<PerformanceReviewDto> getReviewsByEmployeeId(Long employeeId);

    List<PerformanceReviewDto> getTop3ReviewsByEmployeeId(Long employeeId);

    PerformanceReviewDto getReviewById(Long id);

    PerformanceReviewDto createReview(PerformanceReviewDto dto);

    PerformanceReviewDto updateReview(Long id, PerformanceReviewDto dto);

    void deleteReview(Long id);
}
