package com.altisource.hubzu.dashboard.network;

/**
 * Created by naraykan on 11/11/16.
 */

public class PendingIncident {
    private String errorMessage;
    private String stackTrace;
    private Integer numberOfUsersImpacted;

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public Integer getNumberOfUsersImpacted() {
        return numberOfUsersImpacted;
    }
}
