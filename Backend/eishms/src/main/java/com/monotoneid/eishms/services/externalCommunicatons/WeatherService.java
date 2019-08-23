package com.monotoneid.eishms.services.externalcommunicatons;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.gson.*;
import net.minidev.json.*;

import com.monotoneid.eishms.datapersistence.models.HomeDetails;
import com.monotoneid.eishms.datapersistence.models.Weather;
import com.monotoneid.eishms.datapersistence.repositories.Weathers;
import com.monotoneid.eishms.services.filemanagement.HomeDetailsService;

/**
 * .
 */
@Service
@EnableScheduling
@EnableAsync
public class WeatherService {

    @Autowired
    private HttpConnection connection;

    @Autowired
    private Weathers weatherRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private HomeDetailsService homeDetailsService;

    private String parameters = "&key=a3c3c82616b444acad57349c4ad64cbd";
    private String location;
    private String api = "https://api.weatherbit.io/v2.0/current?";
    private final long rate = 900000;
    private final long delay = 15000;

    /** 
     * Sends the current weather after 15 mins through the socket "weather".
     */
    @Async
    @Scheduled(fixedRate = rate, initialDelay = delay)
    public void getCurrentWeather() {
        try {
            api = "https://api.weatherbit.io/v2.0/current?";
            HomeDetails homeDetails = homeDetailsService.readFromFile();
            location = "lat=" + Double.toString(homeDetails.getHomeLatitude());
            location += "&lon=" + Double.toString(homeDetails.getHomeLongitude());
            api += location + parameters;
            StringBuffer content = connection.getContentFromURL(api);
            if (content != null) {
                JsonObject jsonContent = new JsonParser().parse(content.toString())
                                                        .getAsJsonObject();
                JsonArray weatherArray = jsonContent.get("data").getAsJsonArray();
                JsonObject weatherObject = weatherArray.get(0).getAsJsonObject();
                
                String lastOBTime = weatherObject.get("last_ob_time").getAsString();
                lastOBTime = lastOBTime.replace("T", " ");

                location = weatherObject.get("city_name").getAsString();
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
                if (simpMessagingTemplate != null) {
                    simpMessagingTemplate.convertAndSend("/weather", weather);
                }
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