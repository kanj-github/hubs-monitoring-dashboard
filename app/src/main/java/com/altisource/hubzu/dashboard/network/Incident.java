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
}
