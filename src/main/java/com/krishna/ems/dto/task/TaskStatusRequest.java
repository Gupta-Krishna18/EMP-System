package com.krishna.ems.dto.task;

import com.krishna.ems.entity.TaskStatus;
import jakarta.validation.constraints.NotNull;

public class TaskStatusRequest {

    @NotNull(message = "Task status is required")
    private TaskStatus status;

    public TaskStatusRequest() {
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}