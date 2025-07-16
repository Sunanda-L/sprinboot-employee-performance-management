package com.recruitcrm.employee_performance.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="employee_project")
public class EmployeeProject {

    @EmbeddedId
    private EmployeeProjectId id;
    @ManyToOne
    @MapsId("employeeId")
    @JoinColumn(name="employee_id")
    private Employee employee;

    @ManyToOne
    @MapsId("projectId")
    @JoinColumn(name="project_id")
    private Project project;

    @Column(name="assigned_date")
    private LocalDate assignedDate;

    @Column(name="role")
    private String role;
}
