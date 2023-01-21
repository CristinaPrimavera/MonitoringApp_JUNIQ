package com.juniq.cp.monitoringapp;

public class UrlModel {

    private String serverUrl;
    private String serverName;
    private UrlStatus statusCode;

    public UrlModel(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getStatusCode() {
        return statusCode.getLabel();
    }

    public void setStatusCode(UrlStatus statusCode) {
        this.statusCode = statusCode;
    }
}
