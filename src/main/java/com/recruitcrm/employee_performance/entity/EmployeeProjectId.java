package com.recruitcrm.employee_performance.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EmployeeProjectId implements Serializable {
    private Long employeeId;
    private Long projectId;
}
