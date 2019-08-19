package com.monotoneid.eishms.communications.controller;

import com.monotoneid.eishms.services.externalCommunicatons.WeatherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class WeatherEndPointController {

    @Autowired
    private WeatherService weatherService;

    /**
     * GET METHOD
     * Implements the getCurrentWeather endpoint, to get the latest weather.
     * @return JSONObject with weather data
     */
    @GetMapping("/weather")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RESIDENT') or hasRole('GUEST')")
    public ResponseEntity<Object> getLastWeather() {
        System.out.println("within getLastWeather");
        return weatherService.getLastWeather();
    }
}