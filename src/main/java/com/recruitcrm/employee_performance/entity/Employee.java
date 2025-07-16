package com.recruitcrm.employee_performance.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="email", unique = true, nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name="department_id")
    private Department department;

    @Column(name="date_of_joining")
    private LocalDate dateOfJoining;

    @Column(name="salary")
    private Double salary;

    @ManyToOne
    @JoinColumn(name="manager_id")
    private Employee manager;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<PerformanceReview> reviews;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<EmployeeProject> employeeProjects;

}
