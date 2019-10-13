package com.monotoneid.eishms.services.externalcommunicatons;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * CLASS HTTP CONNECTION SERVICE.
 * Responsible for making http connection to external apis.
 */
@Service
public class HttpConnection {

    private URL url;
    private HttpURLConnection connection;

    /**
     * This function calls the api parsed in and the returns the information is gets.
     * @param api
     * @return StriingBuffer or null.
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
                System.out.println("Success: Received data from api: " + api);
                connection.disconnect();
                return content;
            } else {
                streamReader = new InputStreamReader(connection.getErrorStream());
                connection.disconnect();
                return content;
            }
        } catch (Exception e) {
            System.out.println("Failed to connect to api!" + api);
            System.out.println("Error:  " + e.getMessage() + " " + e.getCause());
            return null;
        }
    }
}