package com.altisource.hubzu.dashboard.network;

/**
 * Created by naraykan on 09/11/16.
 */

public class IncidentProcess {
    private String transactionId;
    private String userId;
    private String sourceId;
    private String componentName;
    private Long createdDate;

    public String getTransactionId() {
        return transactionId;
    }

    public String getUserId() {
        return userId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public String getComponentName() {
        return componentName;
    }

    @Override
    public String toString() {
        return transactionId;
    }
}
