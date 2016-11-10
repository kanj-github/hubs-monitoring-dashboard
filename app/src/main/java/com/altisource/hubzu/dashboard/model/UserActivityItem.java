package com.altisource.hubzu.dashboard.model;

import com.altisource.hubzu.dashboard.Constants;
import com.altisource.hubzu.dashboard.network.UserActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by naraykan on 10/11/16.
 */

public class UserActivityItem {
    // 0 - activity, 1 - more
    int type;

    private String startTime;
    private String endTime;
    private String status;

    public UserActivityItem(int type, String startTime, String endTime, String status) {
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getStatus() {
        return status;
    }

    public static ArrayList<UserActivityItem> getListForAdapter(List<UserActivity> list) {
        ArrayList<UserActivityItem> ret = new ArrayList<>();
        if (list != null && list.size() > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy, HH:mm");
            for (UserActivity i: list) {
                ret.add(new UserActivityItem(
                        0,
                        sdf.format(new Date(i.getStartDate())),
                        sdf.format(new Date(i.getEndDate())),
                        i.getStatus()
                ));
            }
        }

        if (list.size() == Constants.ITEMS_PER_PAGE) {
            ret.add(new UserActivityItem(1, null, null, null));
        }

        return ret;
    }
}
