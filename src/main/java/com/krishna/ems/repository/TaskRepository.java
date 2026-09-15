package com.krishna.ems.repository;

import com.krishna.ems.entity.Task;
import com.krishna.ems.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByProjectId(Long projectId);

    List<Task> findByAssignedToId(Long employeeId);

    long countByAssignedToId(Long employeeId);

    long countByAssignedToIdAndStatus(
            Long employeeId,
            TaskStatus status
    );

    long countByStatus(TaskStatus status);
}