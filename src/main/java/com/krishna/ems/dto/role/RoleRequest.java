package com.krishna.ems.dto.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RoleRequest {

    @NotBlank(message = "Role name is required")
    @Size(min = 2, max = 50,
            message = "Role name must be between 2 and 50 characters")
    private String name;

    @Size(max = 255,
            message = "Description cannot exceed 255 characters")
    private String description;

    public RoleRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toUpperCase();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}