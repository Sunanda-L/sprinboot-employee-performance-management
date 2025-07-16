package com.recruitcrm.employee_performance.repository;

import com.recruitcrm.employee_performance.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
