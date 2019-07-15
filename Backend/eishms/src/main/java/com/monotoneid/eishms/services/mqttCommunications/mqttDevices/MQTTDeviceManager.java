package com.monotoneid.eishms.services.mqttCommunications.mqttDevices;

import com.monotoneid.eishms.dataPersistence.models.Device;
import com.monotoneid.eishms.dataPersistence.repositories.Devices;
import com.monotoneid.eishms.exceptions.ResourceNotFoundException;
import com.monotoneid.eishms.services.databaseManagementSystem.DeviceConsumptionService;

import java.util.ArrayList;
import java.util.List;

import net.minidev.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;




@Service
public class MqttDeviceManager {
    
    @Autowired
    protected DeviceConsumptionService deviceConsumptionService;

    @Autowired
    protected SimpMessagingTemplate simpMessagingTemplate;
    
    private ArrayList<MQTTDevice> mqttDevices;

    public MqttDeviceManager(Devices devicesRepository) {
        List<Device> deviceModels = devicesRepository.findAll();
        mqttDevices = new ArrayList<MQTTDevice>();
        deviceModels.forEach((device) -> {
            addDevice(device);
        });
    }

    public void addDevice(Device newDevice) {
        try {
            mqttDevices.add(new MQTTDevice(newDevice, this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // public List<Object> getDeviceStates() {
    //     List<Object> returnList = new ArrayList<Object>();
    //     mqttDevices.forEach((device) -> {    
    //         JSONObject responseObject = new JSONObject();
    //         responseObject.put("deviceId",device.getId());
    //         responseObject.put("deviceState",device.getCurrentState());
    //         returnList.add(responseObject);
    //     });
    //     return returnList;
    // }
    
    public ResponseEntity<Object> controlDevice(long deviceId, String deviceState) {
        try {
            MQTTDevice foundDevice = null;
            for (int i = 0; i < mqttDevices.size()
                && (foundDevice = mqttDevices.get(i)).getId() != deviceId; i++) {}
            if (foundDevice != null) {
                deviceState.toUpperCase();
                if (deviceState == "ON") {
                    foundDevice.turnOn();
                } else if (deviceState == "OFF") {
                    foundDevice.turnOff();
                } else {
                    throw null;
                }
                JSONObject responseObject = new JSONObject();
                responseObject.put("message", "Device changed state!");
                return new ResponseEntity<>(responseObject, HttpStatus.OK);
            } else {
                throw new ResourceNotFoundException("Device does not exist!");
            }
        } catch (Exception e) {
            String errorMessage = "Error: Failed to change device state!";
            System.out.println(errorMessage);
            if (e.getCause() == null) {
                return new ResponseEntity<>(errorMessage, HttpStatus.PRECONDITION_FAILED);
            } else {
                return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
            }
        }
    }

    public String getDeviceStateById(long deviceId) {
        MQTTDevice foundDevice = mqttDevices.get(0);
        for (int i = 0; i < mqttDevices.size() 
            && (foundDevice = mqttDevices.get(i)).getId() != deviceId; i++) {}
        return foundDevice.getCurrentState();
    }
}