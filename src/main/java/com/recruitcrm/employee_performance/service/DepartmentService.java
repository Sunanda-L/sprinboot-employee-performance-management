package com.recruitcrm.employee_performance.service;

import com.recruitcrm.employee_performance.dto.DepartmentDetailDto;
import com.recruitcrm.employee_performance.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {
    List<DepartmentDto> getAllDepartments();

    DepartmentDto getDepartmentById(Long id);

    DepartmentDetailDto getDepartmentDetails(Long id);

    DepartmentDto createDepartment(DepartmentDto dto);

    DepartmentDto updateDepartment(Long id, DepartmentDto dto);

    void deleteDepartment(Long id);

}
