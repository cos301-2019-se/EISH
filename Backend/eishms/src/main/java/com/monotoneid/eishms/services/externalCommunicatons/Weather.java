package com.monotoneid.eishms.services.externalCommunicatons;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class Weather {
    private String api = "http://api.openweathermap.org/data/2.5/weather";
    private String apiKey = "895db04440e712db9d40e21003d6eff9";
    private String cityId = "";
    URL url;
    private HttpURLConnection connection;

    public StringBuffer getCurrentWeather() {
        try {
            StringBuffer content = null;
            url = new URL(api);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            
            Map<String, String> parameters = new HashMap<>();
            parameters.put("id", cityId);
            parameters.put("APPID", apiKey);

            connection.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
            out.flush();
            out.close();

            connection.setRequestProperty("Content-Type", "application/json");

            if(connection.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
                String inputLine;
                content = new StringBuffer();
                while((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                return content;
            }
            connection.disconnect();
            return content;
        } catch(Exception e) {
            throw null;
        }
    }
}