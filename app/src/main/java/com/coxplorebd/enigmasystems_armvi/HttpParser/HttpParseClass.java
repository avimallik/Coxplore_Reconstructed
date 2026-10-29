package com.coxplorebd.enigmasystems_armvi.HttpParser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Arm_AVI on 4/29/2018.
 */

public class HttpParseClass {

    private String urlString;
    private String response = "";
    private String errorMessage = "";
    private int responseCode = -1;

    public HttpParseClass(String url) {
        this.urlString = url;
    }

    public void ExecutePostRequest() {
        ExecutePostRequest(null);
    }

    public void ExecutePostRequest(HashMap<String, String> postDataParams) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(15000);

            if (postDataParams != null && !postDataParams.isEmpty()) {
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                StringBuilder postData = new StringBuilder();
                boolean first = true;
                for (Map.Entry<String, String> entry : postDataParams.entrySet()) {
                    if (!first) postData.append("&");
                    first = false;
                    postData.append(java.net.URLEncoder.encode(entry.getKey(), "UTF-8"));
                    postData.append("=");
                    postData.append(java.net.URLEncoder.encode(entry.getValue(), "UTF-8"));
                }

                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(postData.toString());
                writer.flush();
                writer.close();
                os.close();
            } else {
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
            }

            connection.connect();
            responseCode = connection.getResponseCode();

            InputStream inputStream;
            if (responseCode >= 200 && responseCode < 300) {
                inputStream = connection.getInputStream();
            } else {
                inputStream = connection.getErrorStream();
            }

            if (inputStream != null) {
                reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                response = sb.toString();
            } else {
                response = "";
            }

        } catch (Exception e) {
            errorMessage = e.getMessage() != null ? e.getMessage() : "Network error";
            response = "";
        } finally {
            try {
                if (reader != null) reader.close();
            } catch (Exception ignored) {
            }

            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public String getResponse() {
        return response;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}