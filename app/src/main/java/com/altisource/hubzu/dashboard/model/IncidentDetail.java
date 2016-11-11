package com.altisource.hubzu.dashboard.model;

/**
 * Created by sunilhl on 10/11/16.
 */

public class IncidentDetail {
    private String transactionId;
    private String userId;
    private String sourceId;
    private String componentName;
    private Long createdDate;

    public IncidentDetail(String transactionId, String userId, String sourceId, Long createdDate, String componentName) {
        this.userId = userId;
        this.sourceId = sourceId;
        this.createdDate = createdDate;
        this.componentName = componentName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
