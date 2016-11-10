package com.altisource.hubzu.dashboard.network;

/**
 * Created by naraykan on 09/11/16.
 */

public class Incident {
    private Long id;
    private String status;
    private Long startTime;
    private String component;
    private String assignedTo;

    public Incident(Long id, String status, Long startTime,String component,String assignedTo) {
        this.id = id;
        this.status = status;
        this.startTime = startTime;
        this.component = component;
        this.assignedTo = assignedTo;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
