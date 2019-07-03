package com.monotoneid.eishms.services.mqttCommunications.mqttDevices;

import java.util.ArrayList;
import java.util.List;

import com.monotoneid.eishms.dataPersistence.models.Device;
import com.monotoneid.eishms.dataPersistence.repositories.Devices;
import com.monotoneid.eishms.exceptions.ResourceNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import net.minidev.json.JSONObject;

@Service
public class MQTTDeviceManager {
    
    private ArrayList<MQTTDevice> mqttDevices;

    public MQTTDeviceManager(Devices devicesRepository) {
        List<Device> deviceModels = devicesRepository.findAll();

        mqttDevices = new ArrayList<MQTTDevice>();
        deviceModels.forEach((device) -> {
            mqttDevices.add(new MQTTDevice(device));
        });
    }

    public void addDevice(Device newDevice) {
        mqttDevices.add(new MQTTDevice(newDevice));
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
}