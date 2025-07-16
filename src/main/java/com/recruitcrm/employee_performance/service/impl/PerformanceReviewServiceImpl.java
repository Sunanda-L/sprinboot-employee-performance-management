package com.recruitcrm.employee_performance.service.impl;

import com.recruitcrm.employee_performance.dto.PerformanceReviewDto;
import com.recruitcrm.employee_performance.entity.Employee;
import com.recruitcrm.employee_performance.entity.PerformanceReview;
import com.recruitcrm.employee_performance.repository.EmployeeRepository;
import com.recruitcrm.employee_performance.repository.PerformanceReviewRepository;
import com.recruitcrm.employee_performance.service.PerformanceReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PerformanceReviewServiceImpl implements PerformanceReviewService {

    private final PerformanceReviewRepository reviewRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public List<PerformanceReviewDto> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PerformanceReviewDto> getReviewsByEmployeeId(Long employeeId) {
        return reviewRepository.findByEmployeeIdOrderByReviewDateDesc(employeeId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PerformanceReviewDto> getTop3ReviewsByEmployeeId(Long employeeId) {
        return reviewRepository.findTop3ByEmployeeIdOrderByReviewDateDesc(employeeId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PerformanceReviewDto getReviewById(Long id) {
        return mapToDTO(reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with ID: " + id)));
    }

    @Override
    public PerformanceReviewDto createReview(PerformanceReviewDto dto) {
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        PerformanceReview review = PerformanceReview.builder()
                .employee(employee)
                .reviewDate(dto.getReviewDate())
                .score(dto.getScore())
                .reviewComments(dto.getReviewComments())
                .build();

        return mapToDTO(reviewRepository.save(review));
    }

    @Override
    public PerformanceReviewDto updateReview(Long id, PerformanceReviewDto dto) {
        PerformanceReview review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        review.setReviewDate(dto.getReviewDate());
        review.setScore(dto.getScore());
        review.setReviewComments(dto.getReviewComments());

        return mapToDTO(reviewRepository.save(review));
    }

    @Override
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    private PerformanceReviewDto mapToDTO(PerformanceReview r) {
        return PerformanceReviewDto.builder()
                .id(r.getId())
                .employeeId(r.getEmployee().getId())
                .reviewDate(r.getReviewDate())
                .score(r.getScore())
                .reviewComments(r.getReviewComments())
                .build();
    }
}
