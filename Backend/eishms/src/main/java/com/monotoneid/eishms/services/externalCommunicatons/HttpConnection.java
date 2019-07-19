package com.monotoneid.eishms.services.externalCommunicatons;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class HttpConnection {

    private URL url;
    private HttpURLConnection connection;

    /**
     * .
     */
    public StringBuffer getContentFromURL(String api) {
        try {
            System.out.println(api);
            StringBuffer content = null;
            url = new URL(api);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            HttpStatus status = HttpStatus.resolve(connection.getResponseCode());
            Reader streamReader = null;

            if (status == HttpStatus.OK) {
                BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
                String inputLine;
                content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                connection.disconnect();
                return content;
            } else {
                streamReader = new InputStreamReader(connection.getErrorStream());
                System.out.println("Stream Reader: " + streamReader.toString());
                connection.disconnect();
                return content;
            }
        } catch (Exception e) {
            System.out.println("Error:  " + e.getMessage() + " " + e.getCause());
            return null;
        }
    }
}