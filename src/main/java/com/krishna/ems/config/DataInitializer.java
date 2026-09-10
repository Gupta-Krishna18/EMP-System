package com.krishna.ems.config;

import com.krishna.ems.entity.*;
import com.krishna.ems.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    public DataInitializer(
            RoleRepository roleRepository,
            DepartmentRepository departmentRepository,
            UserRepository userRepository,
            EmployeeRepository employeeRepository,
            ProjectRepository projectRepository,
            TaskRepository taskRepository
    ) {
        this.roleRepository = roleRepository;
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public void run(String... args) {

        // Prevent duplicate data every time the application starts
        if (roleRepository.count() > 0) {
            return;
        }

        // 1. Create Roles
        Role adminRole = new Role("ADMIN");
        Role managerRole = new Role("MANAGER");
        Role employeeRole = new Role("EMPLOYEE");

        roleRepository.save(adminRole);
        roleRepository.save(managerRole);
        roleRepository.save(employeeRole);

        // 2. Create Department
        Department itDepartment = new Department(
                "IT",
                "Information Technology Department"
        );

        departmentRepository.save(itDepartment);

        // 3. Create User
        User adminUser = new User(
                "admin",
                "admin@ems.com",
                "admin123",
                adminRole
        );

        userRepository.save(adminUser);

        // 4. Create Employee
        Employee manager = new Employee();

        manager.setFirstName("Krishna");
        manager.setLastName("Gupta");
        manager.setEmail("krishna@ems.com");
        manager.setPhone("9876543210");
        manager.setDesignation("Project Manager");
        manager.setJoiningDate(LocalDate.now());
        manager.setUser(adminUser);
        manager.setDepartment(itDepartment);

        employeeRepository.save(manager);

        // 5. Create Project
        Project project = new Project();

        project.setName("Employee Management System");
        project.setDescription(
                "Smart Employee and Task Management System"
        );
        project.setStartDate(LocalDate.now());
        project.setEndDate(LocalDate.now().plusMonths(3));
        project.setStatus(ProjectStatus.IN_PROGRESS);
        project.setManager(manager);

        projectRepository.save(project);

        // 6. Create Task
        Task task = new Task();

        task.setTitle("Create Employee Module");
        task.setDescription(
                "Develop Employee CRUD REST APIs"
        );
        task.setPriority(TaskPriority.HIGH);
        task.setStatus(TaskStatus.TODO);
        task.setDueDate(LocalDate.now().plusDays(7));
        task.setProject(project);
        task.setAssignedTo(manager);

        taskRepository.save(task);

        System.out.println("========================================");
        System.out.println("Sample data inserted successfully!");
        System.out.println("========================================");
    }
}