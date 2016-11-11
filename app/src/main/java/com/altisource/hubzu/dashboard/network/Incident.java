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

  /*  public static ArrayList<Incident> getListForAdapter(List<IncidentProcessDetail> list) {
        ArrayList<Incident> ret = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (IncidentProcessDetail i: list) {
                if (Constants.INCIDENT_PROCESS_DETAIL_STATE_SUCCESS.equals(i.getStatus())) {
                    ret.add(new Incident(
                            0,
                            i.getStage(),
                            null,
                            null
                    ));
                } else {
                    ret.add(new Incident(
                            1,
                            i.getStage(),
                            i.getErrorReason(),
                            i.getStackTrace()
                    ));
                }
            }
        }

        if (list.size() == Constants.ITEMS_PER_PAGE) {
            ret.add(new Incident(2, null, null, null));
        }

        return ret;
    }*/
}
