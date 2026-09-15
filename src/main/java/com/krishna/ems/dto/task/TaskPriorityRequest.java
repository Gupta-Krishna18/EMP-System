package com.krishna.ems.dto.task;

import com.krishna.ems.entity.TaskPriority;
import jakarta.validation.constraints.NotNull;

public class TaskPriorityRequest {

    @NotNull(message = "Task priority is required")
    private TaskPriority priority;

    public TaskPriorityRequest() {
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }
}