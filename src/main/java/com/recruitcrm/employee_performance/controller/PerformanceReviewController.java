package com.recruitcrm.employee_performance.controller;

import com.recruitcrm.employee_performance.dto.PerformanceReviewDto;
import com.recruitcrm.employee_performance.service.PerformanceReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("v1/api/performance/reviews")
@RestController
@RequiredArgsConstructor
public class PerformanceReviewController {

    private final PerformanceReviewService performanceReviewService;
    @GetMapping
    public ResponseEntity<List<PerformanceReviewDto>> getAllPerformanceReviews() {
        List<PerformanceReviewDto> performanceReviews = performanceReviewService.getAllReviews();
        return ResponseEntity.ok(performanceReviews);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerformanceReviewDto> getPerformanceReviewById(@PathVariable Long id) {
        PerformanceReviewDto performanceReviewDto = performanceReviewService.getReviewById(id);
        return ResponseEntity.ok(performanceReviewDto);
    }
    @PostMapping
    public ResponseEntity<PerformanceReviewDto> createPerformanceReview(@RequestBody PerformanceReviewDto performanceReviewDto) {
        return new ResponseEntity<>(performanceReviewService.createReview(performanceReviewDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PerformanceReviewDto> updatePerformanceReviewById(@RequestBody PerformanceReviewDto performanceReviewDto, @PathVariable Long id) {
        return new ResponseEntity<>(performanceReviewService.updateReview(id, performanceReviewDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerformanceReviewById(@PathVariable Long id) {
        performanceReviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}
