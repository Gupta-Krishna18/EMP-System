package com.krishna.ems.service;

import com.krishna.ems.dto.department.DepartmentRequest;
import com.krishna.ems.dto.department.DepartmentResponse;
import com.krishna.ems.entity.Department;
import com.krishna.ems.exception.DuplicateResourceException;
import com.krishna.ems.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import com.krishna.ems.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    // CREATE
    public DepartmentResponse createDepartment(
            DepartmentRequest request
    ) {

        if (departmentRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException(
                    "Department already exists: " + request.getName()
            );
        }

        Department department = new Department();

        department.setName(request.getName());
        department.setDescription(request.getDescription());

        Department savedDepartment =
                departmentRepository.save(department);

        return mapToResponse(savedDepartment);
    }

    // READ ALL
    public List<DepartmentResponse> getAllDepartments() {

        return departmentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // READ BY ID
    public DepartmentResponse getDepartmentById(Long id) {

        Department department =
                departmentRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Department not found with id: " + id
                                )
                        );

        return mapToResponse(department);
    }

    // UPDATE
    public DepartmentResponse updateDepartment(
            Long id,
            DepartmentRequest request
    ) {

        Department department =
                departmentRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Department not found with id: " + id
                                )
                        );

        if (!department.getName().equals(request.getName())
                && departmentRepository.existsByName(request.getName())) {

            throw new ResourceNotFoundException(
                    "Department already exists: " + request.getName()
            );
        }

        department.setName(request.getName());
        department.setDescription(request.getDescription());

        Department updatedDepartment =
                departmentRepository.save(department);

        return mapToResponse(updatedDepartment);
    }

    // DELETE
    public void deleteDepartment(Long id) {

        Department department =
                departmentRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Department not found with id: " + id
                                )
                        );

        departmentRepository.delete(department);
    }

    // Entity → DTO
    private DepartmentResponse mapToResponse(
            Department department
    ) {

        return new DepartmentResponse(
                department.getId(),
                department.getName(),
                department.getDescription()
        );
    }
}