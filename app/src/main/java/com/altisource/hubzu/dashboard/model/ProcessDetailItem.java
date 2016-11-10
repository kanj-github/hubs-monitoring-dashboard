package com.altisource.hubzu.dashboard.model;

import com.altisource.hubzu.dashboard.Constants;
import com.altisource.hubzu.dashboard.network.IncidentProcessDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by naraykan on 10/11/16.
 */

public class ProcessDetailItem {
    // 0 - success, 1 - fail, 2 - more
    private int type;

    private String stage;
    private String error;
    private String stackTrace;

    public ProcessDetailItem(int type, String stage, String error, String stackTrace) {
        this.type = type;
        this.stage = stage;
        this.error = error;
        this.stackTrace = stackTrace;
    }

    public int getType() {
        return type;
    }

    public String getStage() {
        return stage;
    }

    public String getError() {
        return error;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public static ArrayList<ProcessDetailItem> getListForAdapter(List<IncidentProcessDetail> list) {
        ArrayList<ProcessDetailItem> ret = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (IncidentProcessDetail i: list) {
                if (Constants.INCIDENT_PROCESS_DETAIL_STATE_SUCCESS.equals(i.getStatus())) {
                    ret.add(new ProcessDetailItem(
                            0,
                            i.getStage(),
                            null,
                            null
                    ));
                } else {
                    ret.add(new ProcessDetailItem(
                            1,
                            i.getStage(),
                            i.getErrorReason(),
                            i.getStackTrace()
                    ));
                }
            }
        }

        if (list.size() == Constants.ITEMS_PER_PAGE) {
            ret.add(new ProcessDetailItem(2, null, null, null));
        }

        return ret;
    }
}
