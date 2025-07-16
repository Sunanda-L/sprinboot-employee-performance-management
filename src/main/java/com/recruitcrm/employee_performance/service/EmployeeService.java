package com.recruitcrm.employee_performance.service;

import com.recruitcrm.employee_performance.dto.EmployeeDetailDto;
import com.recruitcrm.employee_performance.dto.EmployeeDetailReviewDto;
import com.recruitcrm.employee_performance.dto.EmployeeDto;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeService {

    List<EmployeeDto> getEmployeesByDepartment(List<Long> departmentIds);

    List<EmployeeDto> getEmployeesByProject(List<Long> projectIds);

    List<EmployeeDto> getEmployeesByReviewAndFilters(
            LocalDate reviewDate,
            Integer score,
            List<Long> departmentIds,
            List<Long> projectIds
    );
    List<EmployeeDto> getAllEmployees();
    EmployeeDto getEmployeeById(Long id);
    EmployeeDetailDto getEmployeeDetailById(Long id);
    EmployeeDetailReviewDto getEmployeeDetailReviewById(Long id);
    EmployeeDto createEmployee(EmployeeDto dto);
    EmployeeDto updateEmployee(Long id, EmployeeDto dto);
    void deleteEmployee(Long id);
}
