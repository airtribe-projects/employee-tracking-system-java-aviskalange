# Employee Management System - Spring Boot Application

## Introduction

This project is a **Java-based web application** built with **Spring Boot** for managing employees, departments, and projects in an organization. It supports **advanced features**, including **CRUD operations**, secure authentication using **OAuth2.0 with OpenID Connect**, and robust database interaction. The application implements **role-based access control** and provides **API endpoints** for streamlined data access and management. It incorporates **caching** for performance optimization and includes **unit testing** to ensure code reliability.

---

## Features

### 1. **CRUD Operations**
Manage employees, departments, and projects with full **Create, Read, Update, Delete** functionality:
- **Employees**: Add, update, delete, and view employee details.
- **Departments**: Manage department data.
- **Projects**: Track projects and their details.

### 2. **Advanced Database Interaction**
- Database: **MySQL**.
- Data Layer: Uses **Spring Data JPA** with custom queries for:
    - Search employees by criteria (e.g., name, department, or project).
    - Calculate total project budgets within a department.
    - List unassigned employees.
    - Retrieve employees for a specific project.

### 3. **Authentication and Authorization**
- **OAuth2.0** with **OpenID Connect**:
    - External Identity Providers: Supports Google, Auth0, or Okta.
    - Authorization Code Flow for secure token exchange.
- **Role-Based Access Control**:
    - **ADMIN**: Full access to all functionalities.
    - **MANAGER**: Access limited to their department's employees and projects.
    - **EMPLOYEE**: Restricted to their profile and assigned projects.

### 4. **Caching**
- Uses **Spring Cache** with providers like **EhCache** or **Redis**.
- Frequently cached data includes:
    - Employee lists.
    - Department details.
    - Project statuses.
- Implements automatic **cache invalidation** on updates and deletions.

### 5. **Data Validation and Error Handling**
- Comprehensive input validation to prevent invalid data.
- Detailed error messages with appropriate HTTP status codes.

### 6. **API Endpoints**
#### CRUD Endpoints
- `/employees`: Manage employees (Admin, Manager).
- `/departments`: Manage departments (Admin, Manager).
- `/projects`: Manage projects (Admin, Manager).

#### Role-Specific Access
- `/employees/{id}`: Access for Admin, Manager (same department), or Employee (self).
- `/employees/search`: Search employees (Admin, Manager).
- `/departments/{id}/projects`: List department projects (Admin, Manager).

---

## Prerequisites

- **Java 17** or higher.
- **Gradle** for project building.
- **MySQL** database.
- External Identity Provider account (Google).

---

## Key Technologies

- **Spring Boot**: Framework for application development.
- **Spring Security**: For OAuth2.0 and OIDC authentication.
- **Spring Data JPA**: Database interaction.
- **Spring Cache**: Performance optimization.
- **JUnit & Mockito**: Testing framework.
- **MySQL/H2**: Database.

---