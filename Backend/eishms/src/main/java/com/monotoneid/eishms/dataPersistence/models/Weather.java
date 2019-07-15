package com.monotoneid.eishms.dataPersistence.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity(name = "weather")
@Table(name = "weather")
@EntityListeners(AuditingEntityListener.class)
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "weatherid", columnDefinition = "serial", updatable = false, nullable = false)
    private long weatherid;

    @Column(name = "weatherlocation", columnDefinition = "text", updatable = true, nullable = false)
    private String weatherLocation;

    @Column(name = "weatherdescription", columnDefinition = "text", updatable = true, nullable = false)
    private String weatherDescription;

    @Column(name = "weathericon", columnDefinition = "text", updatable = true, nullable = false)
    private String weatherIcon;

    @Column(name = "weathertemperature", columnDefinition = "float", updatable = true, nullable = false)
    private float weatherTemperature;
    
    @Column(name = "weatherhumidity", columnDefinition = "int", updatable = true, nullable = false)
    private int weatherHumidity;
    
    @Column(name = "weatherwindspeed", columnDefinition = "float", updatable = true, nullable = false)
    private float weatherWindSpeed;

    @Column(name = "weatherpressure", columnDefinition = "float", updatable = true, nullable = false)
    private float weatherPressure;

    @Column(name = "weatherlastobtime", columnDefinition = "timestamp", updatable = true, nullable = false)
    private Timestamp weatherLastOBTime;
    
    public Weather() {}

    public Weather(String newLocation, String newDescription, String newIcon, float newTemp, int newHumidity, float newWindSpeed,
    float newPressure, Timestamp newWeatherLastOBTime) {
        setWeatherLocation(newLocation);
        setWeatherDescription(newDescription);
        setWeatherIcon(newIcon);
        setWeatherTemperature(newTemp);
        setWeatherHumidity(newHumidity);
        setWeatherWindSpeed(newWindSpeed);
        setWeatherPressure(newPressure);
        setWeatherLastOBTime(newWeatherLastOBTime);
    }

    //Getters
    public String getWeatherLocation() {
        return this.weatherLocation;
    }

    public String getWeatherDescription() {
        return this.weatherDescription;
    }

    public String getWeatherIcon() {
        return this.weatherIcon;
    }

    public float getWeatherTemperature() {
        return this.weatherTemperature;
    }

    public int getWeatherHumidity() {
        return this.weatherHumidity;
    }

    public float getWeatherWindSpeed() {
        return this.weatherWindSpeed;
    }

    public float getWeatherPressure() {
        return this.weatherPressure;
    }

    public Timestamp getWeatherLastOBTime() {
        return this.weatherLastOBTime;
    }

    //Setters
    public void setWeatherLocation(String newWeatherLocation) {
        this.weatherLocation = newWeatherLocation;
    }

    public void setWeatherDescription (String newWeatherDescription) {
        this.weatherDescription = newWeatherDescription;
    }

    public void setWeatherIcon(String newWeatherIcon) {
        this.weatherIcon = newWeatherIcon;
    }

    public void setWeatherTemperature(float newWeatherTemperature) {
        this.weatherTemperature = newWeatherTemperature;
    }

    public void setWeatherHumidity(int newWeatherHumidity) {
        this.weatherHumidity = newWeatherHumidity;
    }
    
    public void setWeatherWindSpeed(float newWeatherWindSpeed) {
        this.weatherWindSpeed = newWeatherWindSpeed;
    }

    public void setWeatherPressure(float newWeatherPressure) {
        this.weatherPressure = newWeatherPressure;
    }

    public void setWeatherLastOBTime(Timestamp newWeatherLastOBTime) {
        this.weatherLastOBTime = newWeatherLastOBTime;
    }
}
