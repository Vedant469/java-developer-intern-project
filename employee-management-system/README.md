# Employee Management System

A beginner-friendly Java web application for managing employee records. Built with Spring Boot, Spring MVC, Thymeleaf, Spring Data JPA and an H2 database.

## Features
- Create, view, edit and delete employee records (CRUD)
- Form validation for required fields and email format
- Duplicate email prevention
- Data persistence using a local H2 file database
- Responsive, lightweight HTML interface

## Requirements
- Java 17+
- Maven 3.6+

## Run locally
```bash
mvn spring-boot:run
```
Open http://localhost:8080. The H2 console is available at http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:file:./data/employees`, user `sa`, blank password).

## Project structure
- `Employee.java` — JPA entity and validation rules
- `EmployeeRepository.java` — database access
- `EmployeeController.java` — MVC routes and CRUD operations
- `templates/` — Thymeleaf pages

## Possible improvements
Add login/role-based access, pagination, search, and MySQL configuration.

## Author
Vedant
