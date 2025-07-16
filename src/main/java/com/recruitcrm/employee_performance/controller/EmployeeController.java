package com.recruitcrm.employee_performance.controller;

import com.recruitcrm.employee_performance.dto.EmployeeDetailDto;
import com.recruitcrm.employee_performance.dto.EmployeeDetailReviewDto;
import com.recruitcrm.employee_performance.dto.EmployeeDto;
import com.recruitcrm.employee_performance.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("v1/api/employees")
@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("search")
    public ResponseEntity<List<EmployeeDto>> getEmployeesBySearchType(
            @RequestParam(required = false) LocalDate reviewDate,
            @RequestParam(required = false) Integer score,
            @RequestParam(required = false) List<Long> departmentIds,
            @RequestParam(required = false) List<Long> projectIds
    ) {
            List<EmployeeDto> result;
            if (reviewDate != null && score != null) {
                result = employeeService.getEmployeesByReviewAndFilters(
                    reviewDate, score, departmentIds, projectIds
                );
            } else if (departmentIds != null && !departmentIds.isEmpty()) {
                        result = employeeService.getEmployeesByDepartment(departmentIds);
            } else if (projectIds != null && !projectIds.isEmpty()) {
                result = employeeService.getEmployeesByProject(projectIds);
            } else {
                throw new IllegalArgumentException("Invalid filter combination");
            }
            return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) {
        EmployeeDto employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<EmployeeDetailDto> getEmployeeDetailById(@PathVariable Long id) {
        EmployeeDetailDto detail = employeeService.getEmployeeDetailById(id);
        return ResponseEntity.ok(detail);
    }

    @GetMapping("/{id}/details/review")
    public ResponseEntity<EmployeeDetailReviewDto> getEmployeeDetailReviewById(@PathVariable Long id) {
        EmployeeDetailReviewDto detail = employeeService.getEmployeeDetailReviewById(id);
        return ResponseEntity.ok(detail);
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto) {
        EmployeeDto dto = employeeService.createEmployee(employeeDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployeeById(@RequestBody EmployeeDto employeeDto, @PathVariable Long id) {
        EmployeeDto updated = employeeService.updateEmployee(id, employeeDto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
