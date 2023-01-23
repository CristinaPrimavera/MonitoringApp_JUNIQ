package com.juniq.cp.monitoringapp;

import java.io.IOException;
import java.net.HttpURLConnection;

public class UrlValidator {

    public static UrlStatus getUrlStatusCode(UrlModel urlModel) {
        int responseCode;
        try {
            HttpURLConnection huc = (HttpURLConnection) urlModel.getUrlObject().openConnection();
            responseCode = huc.getResponseCode();

            return switch (responseCode) {
                case HttpURLConnection.HTTP_OK -> UrlStatus.OK;
                case HttpURLConnection.HTTP_NOT_FOUND -> UrlStatus.URL_NOT_FOUND;
                default -> UrlStatus.HTTP_STATUS_NOT_200_OR_404;
            };
        } catch (IOException e) {
            return UrlStatus.FAILED_TO_FETCH_URL;
        }
    }
}
