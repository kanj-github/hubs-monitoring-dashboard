package com.altisource.hubzu.dashboard.model;

import com.altisource.hubzu.dashboard.Constants;
import com.altisource.hubzu.dashboard.network.IncidentProcessDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunilhl on 13/11/16.
 */

public class AIncident {
    private Long id;
    private String status;
    private Long startTime;
    private String component;
    private String assignedTo;
    // 0 - success, 1 - fail, 2 - more
    private int type;

    public AIncident(int type,Long id, String status, Long startTime,String component,String assignedTo) {
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

    public static ArrayList<AIncident> getListForAdapter(List<AIncidentMore> list) {
        ArrayList<AIncident> ret = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (AIncidentMore i: list) {
                    ret.add(new AIncident(
                            0,
                            i.getId(),
                            i.getStatus(),
                            i.getStartTime(),
                            i.getComponent(),
                            i.getAssignedTo()
                    ));
                }
            }


        if (list.size() == Constants.ITEMS_PER_PAGE) {
            ret.add(new AIncident(2, null, null, null,null,null));
        }

        return ret;
    }

}

