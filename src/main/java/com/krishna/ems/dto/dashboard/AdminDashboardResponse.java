package com.krishna.ems.dto.dashboard;

public class AdminDashboardResponse {

    private long totalEmployees;
    private long totalDepartments;
    private long totalProjects;
    private long totalTasks;

    private long completedTasks;
    private long inProgressTasks;
    private long pendingTasks;
    private long cancelledTasks;


    public AdminDashboardResponse() {
    }


    public AdminDashboardResponse(
            long totalEmployees,
            long totalDepartments,
            long totalProjects,
            long totalTasks,
            long completedTasks,
            long inProgressTasks,
            long pendingTasks,
            long cancelledTasks) {

        this.totalEmployees = totalEmployees;
        this.totalDepartments = totalDepartments;
        this.totalProjects = totalProjects;
        this.totalTasks = totalTasks;
        this.completedTasks = completedTasks;
        this.inProgressTasks = inProgressTasks;
        this.pendingTasks = pendingTasks;
        this.cancelledTasks = cancelledTasks;
    }


    public long getTotalEmployees() {
        return totalEmployees;
    }

    public long getTotalDepartments() {
        return totalDepartments;
    }

    public long getTotalProjects() {
        return totalProjects;
    }

    public long getTotalTasks() {
        return totalTasks;
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