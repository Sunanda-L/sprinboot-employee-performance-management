CREATE DATABASE IF NOT EXISTS employee_perf_db;
USE employee_perf_db;

-- Departments
DROP TABLE IF EXISTS department;

CREATE TABLE department (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  budget DOUBLE
);

-- Employees
DROP TABLE IF EXISTS employee;

CREATE TABLE employee (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100),
  email VARCHAR(150) UNIQUE NOT NULL,
  department_id BIGINT,
  date_of_joining DATE,
  salary DOUBLE,
  manager_id BIGINT,
  FOREIGN KEY (department_id) REFERENCES department(id),
  FOREIGN KEY (manager_id) REFERENCES employee(id)
);

-- Projects
DROP TABLE IF EXISTS project;

CREATE TABLE project (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100),
  start_date DATE,
  end_date DATE,
  department_id BIGINT,
  FOREIGN KEY (department_id) REFERENCES department(id)
);


-- Employee_Project
DROP TABLE IF EXISTS employee_project;

CREATE TABLE employee_project (
  employee_id BIGINT NOT NULL,
  project_id BIGINT NOT NULL,
  assigned_date DATE,
  role VARCHAR(100),
  PRIMARY KEY (employee_id, project_id),
  FOREIGN KEY (employee_id) REFERENCES employee(id),
  FOREIGN KEY (project_id) REFERENCES project(id)
);

-- Performance Reviews
DROP TABLE IF EXISTS performance_review;

CREATE TABLE performance_review (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  employee_id BIGINT NOT NULL,
  review_date DATE,
  score DOUBLE,
  review_comments TEXT,
  FOREIGN KEY (employee_id) REFERENCES employee(id)
);
