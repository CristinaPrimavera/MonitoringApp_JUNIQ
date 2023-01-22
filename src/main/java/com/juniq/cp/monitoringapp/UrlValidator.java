package com.juniq.cp.monitoringapp;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlValidator {

    public static UrlStatus getUrlStatusCode(UrlModel urlModel) {
        int responseCode;
        try {
            URL url = new URL(urlModel.getServerUrl());
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            responseCode = huc.getResponseCode();

            return switch (responseCode) {
                case HttpURLConnection.HTTP_OK -> UrlStatus.OK;
                case HttpURLConnection.HTTP_NOT_FOUND -> UrlStatus.URL_NOT_FOUND;
                default -> UrlStatus.HTTP_STATUS_NOT_200_OR_404;
            };
        } catch (MalformedURLException e) {
            return UrlStatus.URL_MALFORMED;
        } catch (IOException e) {
            return UrlStatus.FAILED_TO_FETCH_URL;
        }
    }
}
