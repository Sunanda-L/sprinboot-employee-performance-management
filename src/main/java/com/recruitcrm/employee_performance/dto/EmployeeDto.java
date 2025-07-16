package com.recruitcrm.employee_performance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private Long id;
    private String name;
    private String email;
    private Long departmentId;
    private LocalDate dateOfJoining;
    private Double salary;
    private Long managerId;
    private List<ProjectAssignmentDto> projectAssignments;
}
