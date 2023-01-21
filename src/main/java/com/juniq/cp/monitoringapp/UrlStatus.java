package com.juniq.cp.monitoringapp;

public enum UrlStatus {
    URL_MALFORMED("URL malformed"),
    FAILED_TO_FETCH_URL("Failed to fetch URL"),
    OK("OK"),
    URL_NOT_FOUND("URL not found"),
    HTTP_STATUS_NOT_200_OR_404("HTTP status not 200 or 404");

    public String getLabel() {
        return label;
    }

    String label;

    UrlStatus(String label) {
        this.label = label;
    }
}
