package com.krishna.ems.dto.employee;

import java.time.LocalDate;

public class EmployeeResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String designation;
    private LocalDate joiningDate;

    private Long departmentId;
    private String departmentName;

    public EmployeeResponse() {
    }

    public EmployeeResponse(
            Long id,
            String firstName,
            String lastName,
            String email,
            String phone,
            String designation,
            LocalDate joiningDate,
            Long departmentId,
            String departmentName) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.designation = designation;
        this.joiningDate = joiningDate;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getDesignation() {
        return designation;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }
}