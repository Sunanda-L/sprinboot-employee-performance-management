package com.recruitcrm.employee_performance.service.impl;

import com.recruitcrm.employee_performance.dto.ProjectDto;
import com.recruitcrm.employee_performance.entity.Department;
import com.recruitcrm.employee_performance.entity.Project;
import com.recruitcrm.employee_performance.repository.DepartmentRepository;
import com.recruitcrm.employee_performance.repository.ProjectRepository;
import com.recruitcrm.employee_performance.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public List<ProjectDto> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectDto getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + id));
        return mapToDTO(project);
    }

    @Override
    public ProjectDto createProject(ProjectDto dto) {
        Department dept = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + dto.getDepartmentId()));

        Project project = Project.builder()
                .name(dto.getName())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .department(dept)
                .build();

        return mapToDTO(projectRepository.save(project));
    }

    @Override
    public ProjectDto updateProject(Long id, ProjectDto dto) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + id));

        Department dept = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + dto.getDepartmentId()));

        project.setName(dto.getName());
        project.setStartDate(dto.getStartDate());
        project.setEndDate(dto.getEndDate());
        project.setDepartment(dept);

        return mapToDTO(projectRepository.save(project));
    }

    @Override
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    private ProjectDto mapToDTO(Project project) {
        return ProjectDto.builder()
                .id(project.getId())
                .name(project.getName())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .departmentId(
                        project.getDepartment() != null ? project.getDepartment().getId() : null
                )
                .build();
    }
}
