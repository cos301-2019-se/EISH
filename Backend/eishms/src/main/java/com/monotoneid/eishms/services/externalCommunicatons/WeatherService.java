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
import org.springframework.stereotype.Service;

import com.google.gson.*;
import net.minidev.json.*;

import com.monotoneid.eishms.dataPersistence.models.Weather;
import com.monotoneid.eishms.dataPersistence.repositories.Weathers;

@Service
public class WeatherService {

    @Autowired
    private HttpConnection connection;

    @Autowired
    private Weathers weatherRepository;

    // private String iconURL = "http://openweathermap.org/img/wn/";
    // private String apiKey = "895db04440e712db9d40e21003d6eff9";
    // private String city = "Pretoria,za";
    // private String api = "http://api.openweathermap.org/data/2.5/weather?q="+city+"&APPID="+apiKey;

    // private String iconURL = "http://openweathermap.org/img/wn/";
    private String apiKey = "a3c3c82616b444acad57349c4ad64cbd";
    private String city = "Pretoria,ZA";
    private String api = "https://api.weatherbit.io/v2.0/current?city="+city+"&key="+apiKey;

    public ResponseEntity<Object> getCurrentWeather() {
        try {
            StringBuffer content = connection.getContentFromURL(api);
            System.out.println(api);
            JsonObject jsonContent = new JsonParser().parse(content.toString()).getAsJsonObject();
            JsonArray weatherArray = jsonContent.get("data").getAsJsonArray();
            JsonObject weatherObject = weatherArray.get(0).getAsJsonObject();
            
            String lastOBTime = weatherObject.get("last_ob_time").getAsString();
            lastOBTime = lastOBTime.replace("T", " ");

            Weather newWeather = new Weather(
                weatherObject.get("city_name").getAsString(),
                weatherObject.get("weather").getAsJsonObject().get("description").getAsString(),
                weatherObject.get("weather").getAsJsonObject().get("icon").getAsString(),
                weatherObject.get("temp").getAsFloat(),
                weatherObject.get("rh").getAsInt(),
                weatherObject.get("wind_spd").getAsFloat(),
                weatherObject.get("pres").getAsFloat(),
                Timestamp.valueOf(lastOBTime)
            );
            
            weatherRepository.save(newWeather);            

            long lastIndex = weatherRepository.count();
            Weather currentWeather = weatherRepository.getOne(lastIndex);///.orElseThrow(() -> new ResourceNotFoundException("Could not retrieve weather!"));
            JSONObject weather = new JSONObject();
            weather.put("weatherLocation",currentWeather.getWeatherLocation());
            weather.put("weatherDescription",currentWeather.getWeatherDescription());
            weather.put("weatherIcon",currentWeather.getWeatherIcon());
            weather.put("weatherTemperature",currentWeather.getWeatherTemperature());
            weather.put("weatherHumidity",currentWeather.getWeatherHumidity());
            weather.put("weatherWindSpeed",currentWeather.getWeatherWindSpeed());
            weather.put("weatherPressure",currentWeather.getWeatherPressure());
            weather.put("weatherDateTime",currentWeather.getWeatherLastOBTime());
            
            //System.out.println("Is json: " + jsonContent.isJsonObject());
            //System.out.println("Weather: " + jsonContent.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("description").getAsString());
            return new ResponseEntity<>(weather,HttpStatus.OK);
        } catch(Exception e) {
            System.out.println("Error:  " + e.getMessage() + " " + e.getCause());
            throw null;
        }
    }
}