package com.monotoneid.eishms.mqtt_devices;

import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

public class MqttDevice {
    private String device_name;
    private String publish_topic;
    private String subscribe_topic;
    private boolean device_state;
    private String clientId; 
    private MqttAsyncClient asyncClient;
    

    //check actual device state, if start monitoring/subscribe power usage and enter in database
    //else unsubscribe to power usage

    //write a control device method that will publish commands to actual device
    //once the device has completed the action notify frontend

    //monitor device state if state changes notify frontend via websockests


}