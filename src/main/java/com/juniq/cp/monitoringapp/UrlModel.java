package com.juniq.cp.monitoringapp;

public class UrlModel {

    private String serverUrl;
    private String statusCode;

    public UrlModel(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
}
