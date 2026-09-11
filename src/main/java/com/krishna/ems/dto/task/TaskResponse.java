package com.krishna.ems.dto.task;

import com.krishna.ems.entity.TaskPriority;
import com.krishna.ems.entity.TaskStatus;

import java.time.LocalDate;

public class TaskResponse {

    private Long id;
    private String title;
    private String description;
    private TaskPriority priority;
    private TaskStatus status;
    private LocalDate dueDate;

    private Long projectId;
    private String projectName;

    private Long assignedToId;
    private String assignedToName;


    public TaskResponse() {
    }


    public TaskResponse(
            Long id,
            String title,
            String description,
            TaskPriority priority,
            TaskStatus status,
            LocalDate dueDate,
            Long projectId,
            String projectName,
            Long assignedToId,
            String assignedToName) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.dueDate = dueDate;
        this.projectId = projectId;
        this.projectName = projectName;
        this.assignedToId = assignedToId;
        this.assignedToName = assignedToName;
    }


    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Long getProjectId() {
        return projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public Long getAssignedToId() {
        return assignedToId;
    }

    public String getAssignedToName() {
        return assignedToName;
    }
}