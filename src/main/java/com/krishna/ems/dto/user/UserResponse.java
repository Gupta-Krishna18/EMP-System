package com.krishna.ems.dto.user;

public class UserResponse {

    private Long id;
    private String username;
    private String email;

    private Long roleId;
    private String roleName;

    public UserResponse() {
    }

    public UserResponse(
            Long id,
            String username,
            String email,
            Long roleId,
            String roleName) {

        this.id = id;
        this.username = username;
        this.email = email;
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Long getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }
}