package com.krishna.ems.service;

import com.krishna.ems.dto.role.RoleRequest;
import com.krishna.ems.dto.role.RoleResponse;
import com.krishna.ems.entity.Role;
import com.krishna.ems.exception.DuplicateResourceException;
import com.krishna.ems.exception.ResourceNotFoundException;
import com.krishna.ems.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    // CREATE
    public RoleResponse createRole(RoleRequest request) {

        if (roleRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException(
                    "Role already exists: " + request.getName()
            );
        }

        Role role = new Role();

        role.setName(request.getName());
        role.setDescription(request.getDescription());

        Role savedRole = roleRepository.save(role);

        return mapToResponse(savedRole);
    }

    // GET ALL
    public List<RoleResponse> getAllRoles() {

        return roleRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // GET BY ID
    public RoleResponse getRoleById(Long id) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Role not found with id: " + id
                        )
                );

        return mapToResponse(role);
    }

    // UPDATE
    public RoleResponse updateRole(
            Long id,
            RoleRequest request) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Role not found with id: " + id
                        )
                );

        // Check whether the new name already belongs to another role
        if (!role.getName().equals(request.getName())
                && roleRepository.existsByName(request.getName())) {

            throw new DuplicateResourceException(
                    "Role already exists: " + request.getName()
            );
        }

        role.setName(request.getName());
        role.setDescription(request.getDescription());

        Role updatedRole = roleRepository.save(role);

        return mapToResponse(updatedRole);
    }

    // DELETE
    public void deleteRole(Long id) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Role not found with id: " + id
                        )
                );

        roleRepository.delete(role);
    }

    // ENTITY → RESPONSE DTO
    private RoleResponse mapToResponse(Role role) {

        return new RoleResponse(
                role.getId(),
                role.getName(),
                role.getDescription()
        );
    }
}