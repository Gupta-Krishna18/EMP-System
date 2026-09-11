package com.krishna.ems.service;

import com.krishna.ems.dto.project.ProjectRequest;
import com.krishna.ems.dto.project.ProjectResponse;
import com.krishna.ems.entity.Employee;
import com.krishna.ems.entity.Project;
import com.krishna.ems.repository.EmployeeRepository;
import com.krishna.ems.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;

    public ProjectService(
            ProjectRepository projectRepository,
            EmployeeRepository employeeRepository) {

        this.projectRepository = projectRepository;
        this.employeeRepository = employeeRepository;
    }


    // CREATE
    public ProjectResponse createProject(ProjectRequest request) {

        // Check start date and end date
        validateProjectDates(
                request.getStartDate(),
                request.getEndDate()
        );

        // Find manager
        Employee manager = employeeRepository.findById(
                request.getManagerId()
        ).orElseThrow(() ->
                new RuntimeException(
                        "Manager employee not found with id: "
                                + request.getManagerId()
                )
        );

        // Create Project entity
        Project project = new Project();

        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setStartDate(request.getStartDate());
        project.setEndDate(request.getEndDate());
        project.setStatus(request.getStatus());

        // Set Employee as Project manager
        project.setManager(manager);

        // Save project
        Project savedProject = projectRepository.save(project);

        // Entity → Response DTO
        return mapToResponse(savedProject);
    }


    // GET ALL
    public List<ProjectResponse> getAllProjects() {

        return projectRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    // GET BY ID
    public ProjectResponse getProjectById(Long id) {

        Project project = projectRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Project not found with id: " + id
                        )
                );

        return mapToResponse(project);
    }


    // UPDATE
    public ProjectResponse updateProject(
            Long id,
            ProjectRequest request) {

        // Find existing project
        Project project = projectRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Project not found with id: " + id
                        )
                );

        // Validate dates
        validateProjectDates(
                request.getStartDate(),
                request.getEndDate()
        );

        // Find new manager
        Employee manager = employeeRepository.findById(
                request.getManagerId()
        ).orElseThrow(() ->
                new RuntimeException(
                        "Manager employee not found with id: "
                                + request.getManagerId()
                )
        );

        // Update project fields
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setStartDate(request.getStartDate());
        project.setEndDate(request.getEndDate());
        project.setStatus(request.getStatus());

        // Update manager
        project.setManager(manager);

        // Save updated project
        Project updatedProject = projectRepository.save(project);

        return mapToResponse(updatedProject);
    }


    // DELETE
    public void deleteProject(Long id) {

        Project project = projectRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Project not found with id: " + id
                        )
                );

        projectRepository.delete(project);
    }


    // BUSINESS VALIDATION
    private void validateProjectDates(
            java.time.LocalDate startDate,
            java.time.LocalDate endDate) {

        if (endDate.isBefore(startDate)) {
            throw new RuntimeException(
                    "End date cannot be before start date"
            );
        }
    }


    // ENTITY → RESPONSE DTO
    private ProjectResponse mapToResponse(Project project) {

        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getStartDate(),
                project.getEndDate(),
                project.getStatus(),
                project.getManager().getId(),
                project.getManager().getFirstName()
                        + " "
                        + project.getManager().getLastName()
        );
    }
}