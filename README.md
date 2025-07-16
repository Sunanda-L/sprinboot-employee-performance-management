# Employee Performance Management System

A full-featured RESTful web application built with Spring Boot and MySQL for managing employee records, department assignments, project allocations, and performance reviews.

# How to setup the application

# 1. Clone the repository

git clone https://github.com/Sunanda-L/sprinboot-employee-performance-management.git

## 2. Configure MySQL Database

Create a database named employee_perf_db on MySQL and configure your application.properties file as given below

spring.datasource.url=jdbc:mysql://localhost:3306/employee_perf_db
spring.datasource.username=root
spring.datasource.password=your_password

### 3. Run the application

./mvnw spring-boot:run

#### 4. API Endpoints

| Method | Endpoint                                | Description                                   |
| ------ | --------------------------------------- | ----------------------------------------------|
| GET    | `/v1/api/employees`                     | Get all employees                             |
| GET    | `/v1/api/employees/{id}`                | Get employee by ID                            |
| GET    | `/v1/api/employees/{id}/details`        | Get employee detail view with project and dept|
| GET    | `/v1/api/employees/{id}/details/review` | Get employee detail view with last 3 reviews  |
| GET    | `/v1/api/employees/search`              | Filter employees by review/dept/project       |
| POST   | `/v1/api/employees`                     | Create new employee                           |
| PUT    | `/v1/api/employees/{id}`                | Update employee by ID                         |
| DELETE | `/v1/api/employees/{id}`                | Delete employee                               |
| GET    | `/v1/api/departments`                   | Get all departments                           |
| GET    | `/v1/api/departments/{id}`              | Get department details                        |
| GET    | `/v1/api/departments/details/{id}`      | Get department with employees and projects    |
| POST   | `/v1/api/departments`                   | Create a department                           |
| PUT    | `/v1/api/departments/{id}`              | Update a department                           |
| DELETE | `/v1/api/departments/{id}`              | Delete a department                           |
| GET    | `/v1/api/projects`                      | Get all projects                              |
| GET    | `/v1/api/projects/{id}`                 | Get a project by ID                           |
| POST   | `/v1/api/projects`                      | Create a new project                          |
| PUT    | `/v1/api/projects/{id}`                 | Update a project                              |
| DELETE | `/v1/api/projects/{id}`                 | Delete a project                              |
| GET    | `/v1/api/performance/reviews`           | Get all reviews                               |
| GET    | `/v1/api/performance/reviews/{id}`      | Get a review by ID                            |
| POST   | `/v1/api/performance/reviews`           | Create a new review                           |
| PUT    | `/v1/api/performance/reviews/{id}`      | Update a review                               |
| DELETE | `/v1/api/performance/reviews/{id}`      | Delete a review                               |


##### 5. Sample Payloads

Create department
{
    "name": "Human Resources",
    "budget": 600000
}

Create Employee
{
    "name": "John Doe",
    "email": "john.doe@xyz.com",
    "departmentId": 1,
    "dateOfJoining": "2025-07-17",
    "salary": 35000,
    "projectAssignments": [
        {
          "projectId": 1,
                  "assignedDate": "2025-07-20",
                  "role": "Backend Developer"
        }
    ]
}

Create Project
{
    "name": "Project 1",
    "startDate": "2025-07-20",
    "endDate": "2026-07-20",
    "departmentId": 1
}

Create review
{
    "employeeId": 1,
    "reviewDate": "2025-07-16",
    "score": 70,
    "reviewComments": "Need to improve"
}
