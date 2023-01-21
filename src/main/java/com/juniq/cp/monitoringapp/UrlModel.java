package com.juniq.cp.monitoringapp;

public class UrlModel {

    private String serverUrl;
    private UrlStatus statusCode;

    public UrlModel(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public String getStatusCode() {
        return statusCode.getLabel();
    }

    public void setStatusCode(UrlStatus statusCode) {
        this.statusCode = statusCode;
    }
}
