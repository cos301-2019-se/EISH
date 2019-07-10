package com.monotoneid.eishms.services.mqttCommunications.mqttDevices;

import java.util.ArrayList;
import java.util.List;

import com.monotoneid.eishms.dataPersistence.models.Device;
import com.monotoneid.eishms.dataPersistence.repositories.Devices;
import com.monotoneid.eishms.exceptions.ResourceNotFoundException;
import com.monotoneid.eishms.services.databaseManagementSystem.DeviceConsumptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.messaging.simp.*;

import net.minidev.json.JSONObject;

@Service
public class MQTTDeviceManager {
    
    @Autowired
    DeviceConsumptionService deviceConsumptionService;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;
    
    private ArrayList<MQTTDevice> mqttDevices;

    public MQTTDeviceManager(Devices devicesRepository) {
        List<Device> deviceModels = devicesRepository.findAll();
        mqttDevices = new ArrayList<MQTTDevice>();
        deviceModels.forEach((device) -> {
            addDevice(device);
        });
    }

    public void addDevice(Device newDevice) {
        mqttDevices.add(new MQTTDevice(newDevice,this));
    }
    
    public ResponseEntity<Object> controlDevice(long deviceId, String deviceState) {
        try{
            MQTTDevice foundDevice = null;
            for (int i=0; i < mqttDevices.size() && (foundDevice = mqttDevices.get(i)).getId() != deviceId; i++);
            if (foundDevice != null) {
                if(deviceState == "ON")
                    foundDevice.turnOn();
                else if(deviceState == "OFF")
                    foundDevice.turnOff();
                else if(deviceState == "TOGGLE")
                    foundDevice.toggle();
                else
                    throw null;
                JSONObject responseObject = new JSONObject();
                responseObject.put("message","Device changed state!");
                return new ResponseEntity<>(responseObject,HttpStatus.OK);
            } else
                throw new ResourceNotFoundException("Device does not exist!");
        } catch(Exception e) {
            System.out.println("Error: Failed to change device state!");
            if(e.getCause() == null)
                return new ResponseEntity<>("Error: Failed to change device state!",HttpStatus.PRECONDITION_FAILED);
            else
                return new ResponseEntity<>("Error: Failed to change device state!",HttpStatus.NOT_FOUND);
        }
    }

    public String getDeviceStateById(long deviceId) {
        MQTTDevice foundDevice = mqttDevices.get(0);
        for (int i=0; i < mqttDevices.size() && (foundDevice = mqttDevices.get(i)).getId() != deviceId; i++);
        return foundDevice.getCurrentState();
    }
}