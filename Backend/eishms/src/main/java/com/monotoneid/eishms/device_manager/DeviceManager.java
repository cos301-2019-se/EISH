package com.monotoneid.eishms.device_manager;

import java.util.ArrayList;
import java.util.List;

import com.monotoneid.eishms.repository.DevicesRepository;
import org.springframework.stereotype.Component;
import com.monotoneid.eishms.mqtt_devices.MqttDevice;
import com.monotoneid.eishms.model.Devices;

@Component("DeviceManager")
public class DeviceManager {

    private ArrayList<MqttDevice> devices;

    public DeviceManager(DevicesRepository deviceRepo) {
        List<Devices> deviceModels = deviceRepo.findAll();
        
        devices = new ArrayList<MqttDevice>();
        deviceModels.forEach((device) -> {
            devices.add(new MqttDevice(device));
        });
    }

    public void addDevice(Devices device){
        devices.add(new MqttDevice(device));
    }

    public void toggle(long devId) {
        MqttDevice currDev = null;
        for (int i=0; i < devices.size() && (currDev = devices.get(i)).getDeviceID() != devId; i++);
        if (currDev != null)
            currDev.toggle();
    }
}