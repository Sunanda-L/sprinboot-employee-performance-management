package com.recruitcrm.employee_performance.service.impl;

import com.recruitcrm.employee_performance.dto.*;
import com.recruitcrm.employee_performance.entity.*;
import com.recruitcrm.employee_performance.repository.*;
import com.recruitcrm.employee_performance.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final ProjectRepository projectRepository;
    private final EmployeeRepository managerRepository;
    private final EmployeeProjectRepository employeeProjectRepository;
    private final PerformanceReviewRepository performanceReviewRepository;

    @Override
    public List<EmployeeDto> getEmployeesByDepartment(List<Long> departmentIds) {
        if (departmentIds == null || departmentIds.isEmpty()) {
            throw new IllegalArgumentException("Department IDs must not be null or empty.");
        }
        return employeeRepository.findByDepartmentIds(departmentIds)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> getEmployeesByProject(List<Long> projectIds) {
        if (projectIds == null || projectIds.isEmpty()) {
            throw new IllegalArgumentException("Project IDs must not be null or empty.");
        }
        return employeeRepository.findByProjectIds(projectIds)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> getEmployeesByReviewAndFilters(
            LocalDate reviewDate,
            Integer score,
            List<Long> departmentIds,
            List<Long> projectIds
    ) {
        if (reviewDate == null || score == null) {
            throw new IllegalArgumentException("Review date and score are required.");
        }
        return employeeRepository.findByReviewDateAndScoreWithOptionalFilters(
                        reviewDate, score, departmentIds, projectIds)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        return mapToDTO(getEmployee(id));
    }

    @Override
    public EmployeeDetailDto getEmployeeDetailById(Long id) {
        Employee emp = getEmployee(id);

        List<EmployeeProject> empProjects = employeeProjectRepository.findByEmployeeId(emp.getId());
        List<PerformanceReview> lastThree = performanceReviewRepository
                .findTop3ByEmployeeIdOrderByReviewDateDesc(emp.getId());

        return EmployeeDetailDto.builder()
                .id(emp.getId())
                .name(emp.getName())
                .email(emp.getEmail())
                .salary(emp.getSalary())
                .dateOfJoining(emp.getDateOfJoining())
                .department(mapToSimpleDepartment(emp.getDepartment()))
                .projects(empProjects.stream()
                        .map(EmployeeProject::getProject)
                        .map(this::mapToSimpleProject)
                        .collect(Collectors.toList()))
                .manager(emp.getManager() != null ? mapToDTO(emp.getManager()) : null)
                .performanceReviews(performanceReviewRepository.findAll().stream()
                        .map(this::mapToReviewDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public EmployeeDetailReviewDto getEmployeeDetailReviewById(Long id) {
        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        List<EmployeeProject> empProjects = employeeProjectRepository.findByEmployeeId(id);

        List<PerformanceReview> lastThree = performanceReviewRepository
                .findTop3ByEmployeeIdOrderByReviewDateDesc(id);

        return EmployeeDetailReviewDto.builder()
                .id(emp.getId())
                .name(emp.getName())
                .email(emp.getEmail())
                .salary(emp.getSalary())
                .dateOfJoining(emp.getDateOfJoining())
                .department(mapToSimpleDepartment(emp.getDepartment()))
                .manager(emp.getManager() != null ? mapToDTO(emp.getManager()) : null)
                .projects(empProjects.stream()
                        .map(EmployeeProject::getProject)
                        .map(this::mapToSimpleProject)
                        .toList())
                .lastThreeReviews(lastThree.stream()
                        .map(this::mapToReviewDTO)
                        .toList())
                .build();
    }

    @Override
    public EmployeeDto createEmployee(EmployeeDto dto) {
        Department dept = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Employee manager = dto.getManagerId() != null
                ? managerRepository.findById(dto.getManagerId()).orElse(null)
                : null;

        Employee emp = Employee.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .salary(dto.getSalary())
                .dateOfJoining(dto.getDateOfJoining())
                .department(dept)
                .manager(manager)
                .build();

        Employee saved = employeeRepository.save(emp);

        List<EmployeeProject> projectLinks = dto.getProjectAssignments().stream()
                .map(pa -> {
                    Project project = projectRepository.findById(pa.getProjectId())
                            .orElseThrow(() -> new RuntimeException("Project not found"));

                    EmployeeProjectId epId = new EmployeeProjectId(saved.getId(), project.getId());

                    return EmployeeProject.builder()
                            .id(epId)
                            .employee(saved)
                            .project(project)
                            .assignedDate(pa.getAssignedDate())
                            .role(pa.getRole())
                            .build();
                })
                .collect(Collectors.toList());

        employeeProjectRepository.saveAll(projectLinks);

        return mapToDTO(saved);
    }

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto dto) {
        Employee emp = getEmployee(id);

        Department dept = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Employee manager = dto.getManagerId() != null
                ? managerRepository.findById(dto.getManagerId()).orElse(null)
                : null;

        emp.setName(dto.getName());
        emp.setEmail(dto.getEmail());
        emp.setSalary(dto.getSalary());
        emp.setDateOfJoining(dto.getDateOfJoining());
        emp.setDepartment(dept);
        emp.setManager(manager);

        employeeProjectRepository.deleteByEmployeeId(emp.getId());

        List<EmployeeProject> updatedLinks = dto.getProjectAssignments().stream()
                .map(pa -> {
                    Project project = projectRepository.findById(pa.getProjectId())
                            .orElseThrow(() -> new RuntimeException("Project not found"));

                    EmployeeProjectId epId = new EmployeeProjectId(emp.getId(), project.getId());

                    return EmployeeProject.builder()
                            .id(epId)
                            .employee(emp)
                            .project(project)
                            .assignedDate(pa.getAssignedDate())
                            .role(pa.getRole())
                            .build();
                })
                .collect(Collectors.toList());

        employeeProjectRepository.saveAll(updatedLinks);
        return mapToDTO(employeeRepository.save(emp));
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeProjectRepository.deleteByEmployeeId(id);
        employeeRepository.deleteById(id);
    }

    private Employee getEmployee(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
    }

    private EmployeeDto mapToDTO(Employee e) {
        List<ProjectAssignmentDto> assignments = employeeProjectRepository.findByEmployeeId(e.getId())
                .stream()
                .map(ep -> ProjectAssignmentDto.builder()
                        .projectId(ep.getProject().getId())
                        .assignedDate(ep.getAssignedDate())
                        .role(ep.getRole())
                        .build())
                .collect(Collectors.toList());

        return EmployeeDto.builder()
                .id(e.getId())
                .name(e.getName())
                .email(e.getEmail())
                .salary(e.getSalary())
                .dateOfJoining(e.getDateOfJoining())
                .departmentId(e.getDepartment() != null ? e.getDepartment().getId() : null)
                .managerId(e.getManager() != null ? e.getManager().getId() : null)
                .projectAssignments(assignments)
                .build();
    }

    private DepartmentDto mapToSimpleDepartment(Department d) {
        return DepartmentDto.builder()
                .id(d.getId())
                .name(d.getName())
                .budget(d.getBudget())
                .build();
    }

    private ProjectDto mapToSimpleProject(Project p) {
        return ProjectDto.builder()
                .id(p.getId())
                .name(p.getName())
                .startDate(p.getStartDate())
                .endDate(p.getEndDate())
                .departmentId(p.getDepartment() != null ? p.getDepartment().getId() : null)
                .build();
    }

    private PerformanceReviewDto mapToReviewDTO(PerformanceReview r) {
        return PerformanceReviewDto.builder()
                .id(r.getId())
                .reviewDate(r.getReviewDate())
                .score(r.getScore())
                .reviewComments(r.getReviewComments())
                .build();
    }
}
