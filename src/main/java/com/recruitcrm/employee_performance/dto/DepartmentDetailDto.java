package com.recruitcrm.employee_performance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentDetailDto {
    private Long id;
    private String name;
    private Double budget;

    private List<EmployeeDto> employees;
    private List<ProjectDto> projects;
}
