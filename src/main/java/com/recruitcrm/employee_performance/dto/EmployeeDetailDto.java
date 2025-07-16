package com.recruitcrm.employee_performance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDetailDto {

    private Long id;
    private String name;
    private String email;
    private DepartmentDto department;
    private LocalDate dateOfJoining;
    private Double salary;
    private EmployeeDto manager;
    private List<ProjectDto> projects;
    private List<PerformanceReviewDto> performanceReviews;
}
