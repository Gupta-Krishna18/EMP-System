package com.krishna.ems.dto.dashboard;

public class EmployeeDashboardResponse {

    private Long employeeId;
    private String employeeName;
    private String designation;
    private String departmentName;

    private long totalProjectsManaged;

    private long totalAssignedTasks;
    private long completedTasks;
    private long inProgressTasks;
    private long pendingTasks;
    private long cancelledTasks;


    public EmployeeDashboardResponse() {
    }


    public EmployeeDashboardResponse(
            Long employeeId,
            String employeeName,
            String designation,
            String departmentName,
            long totalProjectsManaged,
            long totalAssignedTasks,
            long completedTasks,
            long inProgressTasks,
            long pendingTasks,
            long cancelledTasks) {

        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.designation = designation;
        this.departmentName = departmentName;
        this.totalProjectsManaged = totalProjectsManaged;
        this.totalAssignedTasks = totalAssignedTasks;
        this.completedTasks = completedTasks;
        this.inProgressTasks = inProgressTasks;
        this.pendingTasks = pendingTasks;
        this.cancelledTasks = cancelledTasks;
    }


    public Long getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getDesignation() {
        return designation;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public long getTotalProjectsManaged() {
        return totalProjectsManaged;
    }

    public long getTotalAssignedTasks() {
        return totalAssignedTasks;
    }

    public long getCompletedTasks() {
        return completedTasks;
    }

    public long getInProgressTasks() {
        return inProgressTasks;
    }

    public long getPendingTasks() {
        return pendingTasks;
    }

    public long getCancelledTasks() {
        return cancelledTasks;
    }
}