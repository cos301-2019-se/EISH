package com.monotoneid.eishms.mqtt_devices;

import java.util.UUID;

import com.monotoneid.eishms.model.Devices;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttToken;


//If you wanna code this resources are
//http://www.yasith.me/2016/04/setting-up-mqtt-client-using-eclipse.html to get started
//https://www.eclipse.org/paho/files/javadoc/index.html
//specifically : MqttAsyncClient, MqttToken, and IMqttMessageListener
//https://www.baeldung.com/websockets-spring
//https://www.oracle.com/corporate/features/simple-messaging-with-mqtt.html a bit complicated but you'll manage

//please asynchronous client MqttAsyncClient

public class MqttDevice {
    private Devices device;
    private String consumptionMessage;
    private String powerMessage;
    private String asyncClientId; 
    private IMqttAsyncClient asyncClient;
    private IMqttToken connectToken; 
    //private DeviceConsumptionRepository dcr; //take as pointer from controller and store here! via constructor
    public MqttDevice(Devices dev) {
        this.device = dev;
        asyncClientId = UUID.randomUUID().toString();
        try {
            asyncClient = new MqttAsyncClient("tcp://127.0.0.1:1883", asyncClientId);
            MqttConnectOptions options = new MqttConnectOptions();
            this.connectToken = asyncClient.connect(options);
            this.connectToken.setActionCallback(new IMqttActionListener() {

                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    //this means that we have successfully connected to the browser
                    try {
                        asyncClient.
                        subscribe("cmnd/" + device.getDeviceTopic() + "/Status", 0).
                        setActionCallback(new IMqttActionListener() {
                                @Override
                                public void onSuccess(IMqttToken asyncActionToken2) {
                                    try {
                                        consumptionMessage = asyncActionToken2.getResponse().getPayload().toString();
                                        System.out.println(consumptionMessage);
                                    } catch(MqttException e) {
                                        e.printStackTrace();
                                    }
                                }
                                @Override
                                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                                }
                        });
                        asyncClient.
                        subscribe("cmnd/" + device.getDeviceTopic() + "/RESULT", 0).
                        setActionCallback(new IMqttActionListener() {
                                @Override
                                public void onSuccess(IMqttToken asyncActionToken3) {
                                    try {
                                        powerMessage = asyncActionToken3.getResponse().getPayload().toString();
                                        System.out.println(powerMessage);
                                    } catch(MqttException e) {
                                        e.printStackTrace();
                                    } 
                                }
                                @Override
                                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                                }
                        });
                    } catch(MqttException e) {
                        e.printStackTrace();
                    }
                    
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                }
                
            });
        } catch(MqttException e) {
            e.printStackTrace();
        }
        
    }

    public void toggle() {
        if (!connectToken.isComplete())
            return;
        try {
            asyncClient.publish("cmnd/" + device.getDeviceTopic() + "/Power", new MqttMessage("TOGGLE".getBytes()));    //
        } catch(MqttException e) {
            e.printStackTrace();
        }
        
    }

    public long getDeviceID() {
        return device.getDeviceId();
    }

    //check actual device state, if on start monitoring/subscribe power usage and enter in database
    //(so subscribe callback must contain logic to write to database!)
    //else unsubscribe to power usage

    //write a control device method that will publish commands to physical device
    //once the device has completed the action notify frontend

    //monitor device state if state changes notify frontend via websockests

}