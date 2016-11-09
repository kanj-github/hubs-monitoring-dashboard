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

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
