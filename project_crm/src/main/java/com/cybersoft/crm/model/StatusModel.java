package com.cybersoft.crm.model;

// id , tên trạng thái
public class StatusModel {
    private int id;
    private String statusName;

    public StatusModel() {}

    public StatusModel(int id, String statusName) {
        this.id = id;
        this.statusName = statusName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
