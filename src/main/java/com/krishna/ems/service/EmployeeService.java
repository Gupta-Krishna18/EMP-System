package com.krishna.ems.service;

import com.krishna.ems.dto.employee.EmployeeRequest;
import com.krishna.ems.dto.employee.EmployeeResponse;
import com.krishna.ems.entity.Department;
import com.krishna.ems.entity.Employee;
import com.krishna.ems.entity.User;
import com.krishna.ems.exception.DuplicateResourceException;
import com.krishna.ems.exception.ResourceNotFoundException;
import com.krishna.ems.repository.DepartmentRepository;
import com.krishna.ems.repository.EmployeeRepository;
import com.krishna.ems.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;

    public EmployeeService(
            EmployeeRepository employeeRepository,
            DepartmentRepository departmentRepository,
            UserRepository userRepository) {

        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
    }


    // =========================================================
    // CREATE EMPLOYEE
    // =========================================================

    public EmployeeResponse createEmployee(EmployeeRequest request) {

        // 1. Check duplicate employee email
        if (employeeRepository.existsByEmail(request.getEmail())) {

            throw new DuplicateResourceException(
                    "Employee already exists with email: "
                            + request.getEmail()
            );
        }


        // 2. Find Department
        Department department =
                departmentRepository.findById(request.getDepartmentId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Department not found with id: "
                                                + request.getDepartmentId()
                                )
                        );


        // 3. Find User if userId is provided
        User user = null;

        if (request.getUserId() != null) {

            user = userRepository.findById(request.getUserId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "User not found with id: "
                                            + request.getUserId()
                            )
                    );


            // 4. Check whether User is already linked
            if (employeeRepository.existsByUserId(request.getUserId())) {

                throw new DuplicateResourceException(
                        "User is already linked to an employee"
                );
            }
        }


        // 5. Create Employee entity
        Employee employee = new Employee();

        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setDesignation(request.getDesignation());
        employee.setJoiningDate(request.getJoiningDate());

        // Department relationship
        employee.setDepartment(department);

        // User relationship
        employee.setUser(user);


        // 6. Save Employee
        Employee savedEmployee =
                employeeRepository.save(employee);


        // 7. Entity → Response DTO
        return mapToResponse(savedEmployee);
    }


    // =========================================================
    // GET ALL EMPLOYEES
    // =========================================================

    public List<EmployeeResponse> getAllEmployees() {

        return employeeRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    // =========================================================
    // GET EMPLOYEE BY ID
    // =========================================================

    public EmployeeResponse getEmployeeById(Long id) {

        Employee employee =
                employeeRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Employee not found with id: " + id
                                )
                        );

        return mapToResponse(employee);
    }


    // =========================================================
    // GET EMPLOYEE BY USER ID
    // =========================================================

    public EmployeeResponse getEmployeeByUserId(Long userId) {

        Employee employee =
                employeeRepository.findByUserId(userId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Employee not found for user id: "
                                                + userId
                                )
                        );

        return mapToResponse(employee);
    }


    // =========================================================
    // UPDATE EMPLOYEE
    // =========================================================

    public EmployeeResponse updateEmployee(
            Long id,
            EmployeeRequest request) {

        // 1. Find existing employee
        Employee employee =
                employeeRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Employee not found with id: "
                                                + id
                                )
                        );


        // 2. Check duplicate email
        if (!employee.getEmail().equals(request.getEmail())
                && employeeRepository.existsByEmail(request.getEmail())) {

            throw new DuplicateResourceException(
                    "Employee already exists with email: "
                            + request.getEmail()
            );
        }


        // 3. Find Department
        Department department =
                departmentRepository.findById(request.getDepartmentId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Department not found with id: "
                                                + request.getDepartmentId()
                                )
                        );


        // 4. Find User if userId is provided
        User user = null;

        if (request.getUserId() != null) {

            user = userRepository.findById(request.getUserId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "User not found with id: "
                                            + request.getUserId()
                            )
                    );


            // 5. Check whether User belongs to another Employee
            Optional<Employee> existingEmployee =
                    employeeRepository.findByUserId(
                            request.getUserId()
                    );

            if (existingEmployee.isPresent()
                    && !existingEmployee.get()
                    .getId()
                    .equals(id)) {

                throw new DuplicateResourceException(
                        "User is already linked to another employee"
                );
            }
        }


        // 6. Update Employee fields
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setDesignation(request.getDesignation());
        employee.setJoiningDate(request.getJoiningDate());

        // Update Department
        employee.setDepartment(department);

        // Update User
        employee.setUser(user);


        // 7. Save updated employee
        Employee updatedEmployee =
                employeeRepository.save(employee);


        // 8. Entity → Response DTO
        return mapToResponse(updatedEmployee);
    }


    // =========================================================
    // DELETE EMPLOYEE
    // =========================================================

    public void deleteEmployee(Long id) {

        Employee employee =
                employeeRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Employee not found with id: "
                                                + id
                                )
                        );

        employeeRepository.delete(employee);
    }


    // =========================================================
    // ENTITY → RESPONSE DTO
    // =========================================================

    private EmployeeResponse mapToResponse(Employee employee) {

        Long userId = null;
        String username = null;
        String roleName = null;


        // Employee may not have a User
        if (employee.getUser() != null) {

            userId = employee.getUser().getId();

            username = employee.getUser().getUsername();

            if (employee.getUser().getRole() != null) {

                roleName = employee.getUser().getRole().getName();
            }
        }


        return new EmployeeResponse(

                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getDesignation(),
                employee.getJoiningDate(),

                employee.getDepartment().getId(),
                employee.getDepartment().getName(),

                userId,
                username,
                roleName
        );
    }

    public List<EmployeeResponse> searchEmployees(
            String keyword) {

        List<Employee> employees =
                employeeRepository
                        .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                                keyword,
                                keyword
                        );

        return employees.stream()
                .map(this::mapToResponse)
                .toList();
    }

    public Page<EmployeeResponse> getEmployees(
            int page,
            int size) {

        Pageable pageable =
                PageRequest.of(page, size);

        Page<Employee> employeePage =
                employeeRepository.findAll(pageable);

        return employeePage.map(
                this::mapToResponse
        );
    }
}