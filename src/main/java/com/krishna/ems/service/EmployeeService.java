package com.krishna.ems.service;

import com.krishna.ems.dto.employee.EmployeeRequest;
import com.krishna.ems.dto.employee.EmployeeResponse;
import com.krishna.ems.entity.Department;
import com.krishna.ems.entity.Employee;
import com.krishna.ems.repository.DepartmentRepository;
import com.krishna.ems.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeService(
            EmployeeRepository employeeRepository,
            DepartmentRepository departmentRepository) {

        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    // CREATE
    public EmployeeResponse createEmployee(EmployeeRequest request) {

        // Check duplicate email
        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException(
                    "Employee already exists with email: " + request.getEmail()
            );
        }

        // Find department
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Department not found with id: " + request.getDepartmentId()
                        )
                );

        // Create Employee entity
        Employee employee = new Employee();

        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setDesignation(request.getDesignation());
        employee.setJoiningDate(request.getJoiningDate());

        // Set Department relationship
        employee.setDepartment(department);

        // Save employee
        Employee savedEmployee = employeeRepository.save(employee);

        // Convert Entity → Response DTO
        return mapToResponse(savedEmployee);
    }


    // GET ALL
    public List<EmployeeResponse> getAllEmployees() {

        return employeeRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    // GET BY ID
    public EmployeeResponse getEmployeeById(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Employee not found with id: " + id
                        )
                );

        return mapToResponse(employee);
    }


    // UPDATE
    public EmployeeResponse updateEmployee(
            Long id,
            EmployeeRequest request) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Employee not found with id: " + id
                        )
                );

        // Check duplicate email
        if (!employee.getEmail().equals(request.getEmail())
                && employeeRepository.existsByEmail(request.getEmail())) {

            throw new RuntimeException(
                    "Employee already exists with email: "
                            + request.getEmail()
            );
        }

        // Find new department
        Department department = departmentRepository.findById(
                request.getDepartmentId()
        ).orElseThrow(() ->
                new RuntimeException(
                        "Department not found with id: "
                                + request.getDepartmentId()
                )
        );

        // Update employee fields
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setDesignation(request.getDesignation());
        employee.setJoiningDate(request.getJoiningDate());

        // Update department
        employee.setDepartment(department);

        // Save updated employee
        Employee updatedEmployee = employeeRepository.save(employee);

        return mapToResponse(updatedEmployee);
    }


    // DELETE
    public void deleteEmployee(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Employee not found with id: " + id
                        )
                );

        employeeRepository.delete(employee);
    }


    // ENTITY → RESPONSE DTO
    private EmployeeResponse mapToResponse(Employee employee) {

        return new EmployeeResponse(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getDesignation(),
                employee.getJoiningDate(),
                employee.getDepartment().getId(),
                employee.getDepartment().getName()
        );
    }
}