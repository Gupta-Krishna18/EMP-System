package com.krishna.ems.dto.project;

import com.krishna.ems.entity.ProjectStatus;

import java.time.LocalDate;

public class ProjectResponse {

    private Long id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private ProjectStatus status;

    private Long managerId;
    private String managerName;

    public ProjectResponse() {
    }

    public ProjectResponse(
            Long id,
            String name,
            String description,
            LocalDate startDate,
            LocalDate endDate,
            ProjectStatus status,
            Long managerId,
            String managerName) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.managerId = managerId;
        this.managerName = managerName;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public Long getManagerId() {
        return managerId;
    }

    public String getManagerName() {
        return managerName;
    }
}