package com.cybersoft.crm.model;

// id , tên quyền , mô tả
public class RoleModel {
    private int id;
    private String roleName;
    private String description;

    public RoleModel() {}

    public RoleModel(int id, String roleName, String description) {
        this.id = id;
        this.roleName = roleName;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
