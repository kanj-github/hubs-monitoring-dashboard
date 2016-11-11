package com.altisource.hubzu.dashboard.model;

import com.altisource.hubzu.dashboard.Constants;
import com.altisource.hubzu.dashboard.network.PendingIncident;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by naraykan on 11/11/16.
 */

public class PendingIncidentItem {
    // 0 - pending incident, 1 - more
    private int type;

    private String errorMessage;
    private String stackTrace;
    private int numberOfUsersImpacted;

    public PendingIncidentItem(int type, PendingIncident pi) {
        this.type = type;
        this.errorMessage = pi.getErrorMessage();
        this.stackTrace = pi.getStackTrace();
        this.numberOfUsersImpacted = pi.getNumberOfUsersImpacted();
    }

    public PendingIncidentItem(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public int getNumberOfUsersImpacted() {
        return numberOfUsersImpacted;
    }

    public static ArrayList<PendingIncidentItem> getListForAdapter(List<PendingIncident> list) {
        ArrayList<PendingIncidentItem> ret = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (PendingIncident i: list) {
                ret.add(new PendingIncidentItem(0, i));
            }
        }

        if (list.size() == Constants.ITEMS_PER_PAGE) {
            ret.add(new PendingIncidentItem(1));
        }

        return ret;
    }
}
