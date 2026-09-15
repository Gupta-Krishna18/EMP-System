package com.krishna.ems.service;

import com.krishna.ems.dto.dashboard.AdminDashboardResponse;
import com.krishna.ems.dto.dashboard.EmployeeDashboardResponse;
import com.krishna.ems.entity.Employee;
import com.krishna.ems.entity.TaskStatus;
import com.krishna.ems.exception.ResourceNotFoundException;
import com.krishna.ems.repository.DepartmentRepository;
import com.krishna.ems.repository.EmployeeRepository;
import com.krishna.ems.repository.ProjectRepository;
import com.krishna.ems.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;


    public DashboardService(
            EmployeeRepository employeeRepository,
            DepartmentRepository departmentRepository,
            ProjectRepository projectRepository,
            TaskRepository taskRepository) {

        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }


    // =========================================================
    // EMPLOYEE DASHBOARD
    // =========================================================

    public EmployeeDashboardResponse getEmployeeDashboard(
            Long employeeId) {

        // 1. Find Employee
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id: "
                                        + employeeId
                        )
                );


        // 2. Employee basic information
        String employeeName =
                employee.getFirstName()
                        + " "
                        + employee.getLastName();

        String designation =
                employee.getDesignation();

        String departmentName = null;

        if (employee.getDepartment() != null) {
            departmentName =
                    employee.getDepartment().getName();
        }


        // 3. Projects managed by employee
        long totalProjectsManaged =
                projectRepository.countByManagerId(employeeId);


        // 4. Total assigned tasks
        long totalAssignedTasks =
                taskRepository.countByAssignedToId(employeeId);


        // 5. Task status counts
        long completedTasks =
                taskRepository.countByAssignedToIdAndStatus(
                        employeeId,
                        TaskStatus.COMPLETED
                );

        long inProgressTasks =
                taskRepository.countByAssignedToIdAndStatus(
                        employeeId,
                        TaskStatus.IN_PROGRESS
                );

        long pendingTasks =
                taskRepository.countByAssignedToIdAndStatus(
                        employeeId,
                        TaskStatus.TODO
                );

        long cancelledTasks =
                taskRepository.countByAssignedToIdAndStatus(
                        employeeId,
                        TaskStatus.CANCELLED
                );


        // 6. Create response
        return new EmployeeDashboardResponse(

                employee.getId(),
                employeeName,
                designation,
                departmentName,

                totalProjectsManaged,

                totalAssignedTasks,
                completedTasks,
                inProgressTasks,
                pendingTasks,
                cancelledTasks
        );
    }


    // =========================================================
    // ADMIN DASHBOARD
    // =========================================================

    public AdminDashboardResponse getAdminDashboard() {

        // Total records
        long totalEmployees =
                employeeRepository.count();

        long totalDepartments =
                departmentRepository.count();

        long totalProjects =
                projectRepository.count();

        long totalTasks =
                taskRepository.count();


        // Task status counts
        long completedTasks =
                taskRepository.countByStatus(
                        TaskStatus.COMPLETED
                );

        long inProgressTasks =
                taskRepository.countByStatus(
                        TaskStatus.IN_PROGRESS
                );

        long pendingTasks =
                taskRepository.countByStatus(
                        TaskStatus.TODO
                );

        long cancelledTasks =
                taskRepository.countByStatus(
                        TaskStatus.CANCELLED
                );


        return new AdminDashboardResponse(

                totalEmployees,
                totalDepartments,
                totalProjects,
                totalTasks,

                completedTasks,
                inProgressTasks,
                pendingTasks,
                cancelledTasks
        );
    }
}