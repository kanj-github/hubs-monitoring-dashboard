package com.altisource.hubzu.dashboard.network;

/**
 * Created by naraykan on 09/11/16.
 */

public class IncidentProcessDetail {
    private String stage;
    private String status;
    private String errorReason;
    private String stackTrace;

    public String getStage() {
        return stage;
    }

    public String getStatus() {
        return status;
    }

    public String getErrorReason() {
        return errorReason;
    }

    public String getStackTrace() {
        return stackTrace;
    }
}
