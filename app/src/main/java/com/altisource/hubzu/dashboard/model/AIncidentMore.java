package com.altisource.hubzu.dashboard.model;

/**
 * Created by sunilhl on 13/11/16.
 */

public class AIncidentMore {
    private Long id;
    private String status;
    private Long startTime;
    private String component;
    private String assignedTo;


    public AIncidentMore(Long id, String status, Long startTime,String component,String assignedTo) {
        this.id = id;
        this.status = status;
        this.startTime = startTime;
        this.component = component;
        this.assignedTo = assignedTo;
    }

    public String getComponent() {
        return component;
    }

    public Long getStartTime() {
        return startTime;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public String getStatus() {
        return status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
