package com.recruitcrm.employee_performance.repository;

import com.recruitcrm.employee_performance.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("""
    SELECT e FROM Employee e
    WHERE e.department.id IN :departmentIds
""")
    List<Employee> findByDepartmentIds(@Param("departmentIds") List<Long> departmentIds);

    @Query("""
    SELECT DISTINCT e FROM Employee e
    JOIN EmployeeProject ep ON ep.employee = e
    WHERE ep.project.id IN :projectIds
""")
    List<Employee> findByProjectIds(@Param("projectIds") List<Long> projectIds);

    @Query("""
    SELECT DISTINCT e FROM Employee e
    JOIN PerformanceReview r ON r.employee = e
    LEFT JOIN e.department d
    LEFT JOIN EmployeeProject ep ON ep.employee = e
    LEFT JOIN ep.project p
    WHERE r.reviewDate = :reviewDate
    AND r.score = :score
    AND (:departmentIds IS NULL OR d.id IN :departmentIds)
    AND (:projectIds IS NULL OR p.id IN :projectIds)
    """)
    List<Employee> findByReviewDateAndScoreWithOptionalFilters(
            @Param("reviewDate") LocalDate reviewDate,
            @Param("score") Integer score,
            @Param("departmentIds") List<Long> departmentIds,
            @Param("projectIds") List<Long> projectIds
    );
}
