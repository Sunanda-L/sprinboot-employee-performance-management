package com.recruitcrm.employee_performance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PerformanceReviewDto {
    private Long id;
    private Long employeeId;
    private LocalDate reviewDate;
    private Long score;
    private String reviewComments;
}
