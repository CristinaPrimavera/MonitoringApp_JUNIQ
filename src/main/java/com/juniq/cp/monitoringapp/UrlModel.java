package com.juniq.cp.monitoringapp;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class UrlModel {

    private URL urlObject;
    private String serverName;
    private UrlStatus statusCode;

    public UrlModel() {
    }

    public UrlModel(String serverUrl) throws MalformedURLException, URISyntaxException {
        this.serverName = new URI(serverUrl).getHost();
        this.statusCode = UrlStatus.FETCHING;
        this.urlObject = new URL(serverUrl);
    }

    public URL getUrlObject() {
        return urlObject;
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
