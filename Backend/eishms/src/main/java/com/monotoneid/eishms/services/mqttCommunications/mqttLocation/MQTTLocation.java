package com.monotoneid.eishms.services.mqttcommunications.mqttlocation;

public class MqttLocation {
    String serverUrl = "tcp://127.0.0.1:1883";
    /* This Requires no ssl */
    //String caFilePath = "/your_ssl/cacert.pem";
    //String clientCrtFilePath = "/your_ssl/client.pem";
    //String clientKeyFilePath = "/your_ssl/client.key";
    String mqttUserName = "eishms";
    String mqttPassword = "eishms";

    public MqttLocation() {

    }
} 