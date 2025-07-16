package com.recruitcrm.employee_performance.service.impl;

import com.recruitcrm.employee_performance.dto.DepartmentDetailDto;
import com.recruitcrm.employee_performance.dto.DepartmentDto;
import com.recruitcrm.employee_performance.dto.EmployeeDto;
import com.recruitcrm.employee_performance.dto.ProjectDto;
import com.recruitcrm.employee_performance.entity.Department;
import com.recruitcrm.employee_performance.entity.Employee;
import com.recruitcrm.employee_performance.entity.Project;
import com.recruitcrm.employee_performance.repository.DepartmentRepository;
import com.recruitcrm.employee_performance.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public List<DepartmentDto> getAllDepartments() {
        List<DepartmentDto> departments = departmentRepository.findAll().stream().map(this::mapToDTO)
                .collect(Collectors.toList());
        return departments;
    }


    @Override
    public DepartmentDto getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + id));
        return mapToDTO(department);
    }

    @Override
    public DepartmentDetailDto getDepartmentDetails(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + id));

        List<EmployeeDto> employees = department.getEmployees().stream()
                .map(this::mapEmployeeToDTO)
                .collect(Collectors.toList());

        List<ProjectDto> projects = department.getProjects().stream()
                .map(this::mapProjectToDTO)
                .collect(Collectors.toList());

        return DepartmentDetailDto.builder()
                .id(department.getId())
                .name(department.getName())
                .budget(department.getBudget())
                .employees(employees)
                .projects(projects)
                .build();
    }

    @Override
    public DepartmentDto createDepartment(DepartmentDto dto) {
        Department department = Department.builder()
                .name(dto.getName())
                .budget(dto.getBudget())
                .build();
        return mapToDTO(departmentRepository.save(department));
    }

    @Override
    public DepartmentDto updateDepartment(Long id, DepartmentDto dto) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + id));

        department.setName(dto.getName());
        department.setBudget(dto.getBudget());

        return mapToDTO(departmentRepository.save(department));
    }

    @Override
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

    private DepartmentDto mapToDTO(Department department) {
        return DepartmentDto.builder()
                .id(department.getId())
                .name(department.getName())
                .budget(department.getBudget())
                .build();
    }

    private EmployeeDto mapEmployeeToDTO(Employee emp) {
        return EmployeeDto.builder()
                .id(emp.getId())
                .name(emp.getName())
                .email(emp.getEmail())
                .dateOfJoining(emp.getDateOfJoining())
                .salary(emp.getSalary())
                .departmentId(emp.getDepartment() != null ? emp.getDepartment().getId() : null)
                .managerId(emp.getManager() != null ? emp.getManager().getId() : null)
                .build();
    }

    private ProjectDto mapProjectToDTO(Project project) {
        return ProjectDto.builder()
                .id(project.getId())
                .name(project.getName())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .departmentId(project.getDepartment() != null ? project.getDepartment().getId() : null)
                .build();
    }
}
