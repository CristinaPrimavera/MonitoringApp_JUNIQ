package com.juniq.cp.monitoringapp;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlValidator {

    public static String getUrlStatusCode(UrlModel urlModel) {
        int responseCode;
        try {
            URL url = new URL(urlModel.getServerUrl());
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            responseCode = huc.getResponseCode();
        } catch (MalformedURLException e) {
            return "URL malformed";
        } catch (IOException e) {
            return "Failed to fetch URL";
        }

        return switch (responseCode) {
            case HttpURLConnection.HTTP_OK -> "Successful connection";
            case HttpURLConnection.HTTP_NOT_FOUND -> "URL not found";
            default -> "Status code differs from 200 or 404";
        };
    }
}
