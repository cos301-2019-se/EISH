package com.monotoneid.eishms.services.databaseManagementSystem;

import java.util.List;

import com.monotoneid.eishms.dataPersistence.models.Device;
import com.monotoneid.eishms.dataPersistence.repositories.Devices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {

    @Autowired
    private Devices devicesRepository;

    public List<Device> retrieveAllDevices(){
        return devicesRepository.findAll();
    }
    
}