package com.monotoneid.eishms.datapersistence.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HomeDetails {

    private String homeName;
    private String homeLocation;
    private double homeAltitude;
    private double homeLongitude;
    private double homeLatitude;
    private int homeRadius;

    public HomeDetails(@JsonProperty("homeName") String homeName,
            @JsonProperty("homeLocation") String homeLocation,
            @JsonProperty("homeAltitude") double homeAltitude,
            @JsonProperty("homeLongitude") double homeLongitude,
            @JsonProperty("homeLatitude") double homeLatitude,
            @JsonProperty("homeRadius") int homeRadius) {
        setHomeName(homeName);
        setHomeLocation(homeLocation);
        setHomeAltitude(homeAltitude);
        setHomeLongitude(homeLongitude);
        setHomeLatitude(homeLatitude);
        setHomeRadius(homeRadius);
    }

    //gette
    public String getHomeName() {
        return this.homeName;
    }

    public String getHomeLocation() {
        return this.homeLocation;
    }

    public double getHomeAltitude() {
        return this.homeAltitude;
    }

    public double getHomeLongitude() {
        return this.homeLongitude;
    }

    public double getHomeLatitude() {
        return this.homeLatitude;
    }

    public int getHomeRadius() {
        return this.homeRadius;
    }

    //setter
    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }

    public void setHomeLocation(String homeLocation) {
        this.homeLocation = homeLocation;
    }

    public void setHomeAltitude(double homeAltitude) {
        this.homeAltitude = homeAltitude;
    }

    public void setHomeLongitude(double homeLongitude) {
        this.homeLongitude = homeLongitude;
    }

    public void setHomeLatitude(double homeLatitude) {
        this.homeLatitude = homeLatitude;
    }

    public void setHomeRadius(int homeRadius) {
        this.homeRadius = homeRadius;
    }
}