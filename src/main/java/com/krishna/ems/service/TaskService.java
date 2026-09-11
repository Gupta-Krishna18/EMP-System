package com.krishna.ems.service;

import com.krishna.ems.dto.task.TaskRequest;
import com.krishna.ems.dto.task.TaskResponse;
import com.krishna.ems.entity.Employee;
import com.krishna.ems.entity.Project;
import com.krishna.ems.entity.Task;
import com.krishna.ems.repository.EmployeeRepository;
import com.krishna.ems.repository.ProjectRepository;
import com.krishna.ems.repository.TaskRepository;
import org.springframework.stereotype.Service;
import com.krishna.ems.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;


    public TaskService(
            TaskRepository taskRepository,
            ProjectRepository projectRepository,
            EmployeeRepository employeeRepository) {

        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.employeeRepository = employeeRepository;
    }


    // CREATE
    public TaskResponse createTask(TaskRequest request) {

        // Find project
        Project project = projectRepository.findById(
                request.getProjectId()
        ).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Project not found with id: "
                                + request.getProjectId()
                )
        );


        // Find assigned employee
        Employee employee = employeeRepository.findById(
                request.getAssignedToId()
        ).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Employee not found with id: "
                                + request.getAssignedToId()
                )
        );


        // Create Task entity
        Task task = new Task();

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        task.setStatus(request.getStatus());
        task.setDueDate(request.getDueDate());

        // Set relationships
        task.setProject(project);
        task.setAssignedTo(employee);


        // Save
        Task savedTask = taskRepository.save(task);


        // Entity → Response DTO
        return mapToResponse(savedTask);
    }


    // GET ALL
    public List<TaskResponse> getAllTasks() {

        return taskRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    // GET BY ID
    public TaskResponse getTaskById(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task not found with id: " + id
                        )
                );

        return mapToResponse(task);
    }


    // UPDATE
    public TaskResponse updateTask(
            Long id,
            TaskRequest request) {

        // Find existing task
        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task not found with id: " + id
                        )
                );


        // Find project
        Project project = projectRepository.findById(
                request.getProjectId()
        ).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Project not found with id: "
                                + request.getProjectId()
                )
        );


        // Find assigned employee
        Employee employee = employeeRepository.findById(
                request.getAssignedToId()
        ).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Employee not found with id: "
                                + request.getAssignedToId()
                )
        );


        // Update fields
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        task.setStatus(request.getStatus());
        task.setDueDate(request.getDueDate());

        // Update relationships
        task.setProject(project);
        task.setAssignedTo(employee);


        // Save
        Task updatedTask = taskRepository.save(task);

        return mapToResponse(updatedTask);
    }


    // DELETE
    public void deleteTask(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task not found with id: " + id
                        )
                );

        taskRepository.delete(task);
    }


    // ENTITY → RESPONSE DTO
    private TaskResponse mapToResponse(Task task) {

        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getPriority(),
                task.getStatus(),
                task.getDueDate(),

                task.getProject().getId(),
                task.getProject().getName(),

                task.getAssignedTo().getId(),
                task.getAssignedTo().getFirstName()
                        + " "
                        + task.getAssignedTo().getLastName()
        );
    }
}