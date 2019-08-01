package com.monotoneid.eishms.services.externalCommunicatons;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.gson.*;
import net.minidev.json.*;

import com.monotoneid.eishms.datapersistence.models.Weather;
import com.monotoneid.eishms.datapersistence.repositories.Weathers;

/**
 * .
 */
@Service
public class WeatherService {

    @Autowired
    private HttpConnection connection;

    @Autowired
    private Weathers weatherRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    // private String iconURL = "http://openweathermap.org/img/wn/";
    // private String apiKey = "895db04440e712db9d40e21003d6eff9";
    // private String city = "Pretoria,za";
    // private String api = "http://api.openweathermap.org/data/2.5/weather?q="+city+"&APPID="+apiKey;

    // private String iconURL = "http://openweathermap.org/img/wn/";
    private String parameters = "city=Pretoria,ZA&key=a3c3c82616b444acad57349c4ad64cbd";
    private String api = "https://api.weatherbit.io/v2.0/current?" + parameters;
    private final long rate = 900000;
    private final long delay = 30000;

    /** 
     * Sends the current weather after 15 mins through the socket "weather".
     */
    @Scheduled(fixedRate = rate, initialDelay = delay)
    public void getCurrentWeather() {
        try {
            StringBuffer content = connection.getContentFromURL(api);
            if (content != null) {
                JsonObject jsonContent = new JsonParser().parse(content.toString())
                                                        .getAsJsonObject();
                JsonArray weatherArray = jsonContent.get("data").getAsJsonArray();
                JsonObject weatherObject = weatherArray.get(0).getAsJsonObject();
                
                String lastOBTime = weatherObject.get("last_ob_time").getAsString();
                lastOBTime = lastOBTime.replace("T", " ");

                String location = weatherObject.get("city_name").getAsString();
                JsonObject weatherJson = weatherObject.get("weather").getAsJsonObject();
                float temp = weatherObject.get("temp").getAsFloat();
                int humidity = weatherObject.get("rh").getAsInt();
                float windSpeed = weatherObject.get("wind_spd").getAsFloat();
                float pressure = weatherObject.get("pres").getAsFloat();
                Timestamp lastOB = Timestamp.valueOf(lastOBTime);
                Weather newWeather = new Weather(
                    location,
                    weatherJson.get("description").getAsString(),
                    weatherJson.get("icon").getAsString(),
                    temp,
                    humidity,
                    windSpeed,
                    pressure,
                    lastOB
                );
                System.out.println("Success: Retrived the weather!");               
                weatherRepository.save(newWeather);            

                JSONObject weather = new JSONObject();
                weather.put("weatherLocation", location);
                weather.put("weatherDescription", weatherJson.get("description").getAsString());
                weather.put("weatherIcon", weatherJson.get("icon").getAsString());
                weather.put("weatherTemperature", temp);
                System.out.println("About to send the current weather!");
                simpMessagingTemplate.convertAndSend("/weather", weather);
                System.out.println("Success: Send the current weather!");
            } else {
                System.out.println("Weather is null!");
            }
        } catch (Exception e) {
            System.out.println("Couldn't get weather!");
            System.out.println("Error:  " + e.getMessage() + " " + e.getCause());
        }
    }

    /**
     * .
     * @return
     */
    public ResponseEntity<Object> getLastWeather() {
        try {
            Weather lastWeather = weatherRepository.findLastWeather();
            if (lastWeather == null) {
                System.out.println("There is no weather!");
                throw null;
            } else {
                JSONObject weather = new JSONObject();
                weather.put("weatherLocation", lastWeather.getWeatherLocation());
                weather.put("weatherDescription", lastWeather.getWeatherDescription());
                weather.put("weatherIcon", lastWeather.getWeatherIcon());
                weather.put("weatherTemperature", lastWeather.getWeatherTemperature());
                return new ResponseEntity<>(weather, HttpStatus.OK);
            }
        } catch (Exception e) {
            System.out.println("Error:  " + e.getMessage() + " " + e.getCause());
            throw null;
        }
    }
}