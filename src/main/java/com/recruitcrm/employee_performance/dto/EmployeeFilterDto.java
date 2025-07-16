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
public class EmployeeFilterDto {
    private LocalDate reviewDate;
    private Double minScore;
    private List<Long> departmentIds;
    private List<Long> projectIds;
}
