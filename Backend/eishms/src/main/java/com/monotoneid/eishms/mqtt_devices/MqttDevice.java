package com.monotoneid.eishms.mqtt_devices;

import java.util.UUID;

import com.monotoneid.eishms.model.Device;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttToken;


//If you wanna code this resources are
//http://www.yasith.me/2016/04/setting-up-mqtt-client-using-eclipse.html to get started
//https://www.eclipse.org/paho/files/javadoc/index.html
//specifically : MqttAsyncClient, MqttToken, and IMqttMessageListener
//https://www.baeldung.com/websockets-spring
//https://www.oracle.com/corporate/features/simple-messaging-with-mqtt.html a bit complicated but you'll manage

//please asynchronous client MqttAsyncClient
//Whoever you are I believe in you!!! 

public class MqttDevice {
    private Device device;
    private String asyncClientId; 
    private IMqttAsyncClient asyncClient;
    private IMqttToken connectToken; 
    //private DeviceConsumptionRepository dcr; //take as pointer from controller and store here! via constructor
    public MqttDevice(Device dev) {
        this.device = dev;
        asyncClientId = UUID.randomUUID().toString();
        try {
            asyncClient = new MqttAsyncClient("tcp://127.0.0.1:1883", asyncClientId);
            MqttConnectOptions options = new MqttConnectOptions();
            this.connectToken = asyncClient.connect(options);
            this.connectToken.setActionCallback(new ConnectionCallback());
        } catch(MqttException e) {
            e.printStackTrace();
        }
        
    }

    public class ConnectionCallback implements IMqttActionListener {
        @Override
        public void onFailure(IMqttToken asyncActionToken, java.lang.Throwable exception) {

        }

        @Override
        public void onSuccess(IMqttToken asyncActionToken) {
            
        }
    }

    //check actual device state, if on start monitoring/subscribe power usage and enter in database
    //(so subscribe callback must contain logic to write to database!)
    //else unsubscribe to power usage

    //write a control device method that will publish commands to physical device
    //once the device has completed the action notify frontend

    //monitor device state if state changes notify frontend via websockests

}