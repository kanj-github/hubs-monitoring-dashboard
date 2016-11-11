package com.altisource.hubzu.dashboard.network;

import com.altisource.hubzu.dashboard.Constants;
import com.altisource.hubzu.dashboard.model.ProcessDetailItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by naraykan on 09/11/16.
 */

public class Incident {
    private Long id;
    private String status;
    private Long startTime;
    private String component;
    private String assignedTo;
    // 0 - success, 1 - fail, 2 - more
    private int type;

    public Incident(int type,Long id, String status, Long startTime,String component,String assignedTo) {
        this.type = type;
        this.id = id;
        this.status = status;
        this.startTime = startTime;
        this.component = component;
        this.assignedTo = assignedTo;
    }

    public int getType() {
        return type;
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
