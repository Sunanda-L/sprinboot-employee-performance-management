package com.recruitcrm.employee_performance.repository;

import com.recruitcrm.employee_performance.entity.EmployeeProject;
import com.recruitcrm.employee_performance.entity.EmployeeProjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeProjectRepository extends JpaRepository<EmployeeProject, EmployeeProjectId> {
    List<EmployeeProject> findByEmployeeId(Long employeeId);

    void deleteByEmployeeId(Long employeeId);
}
