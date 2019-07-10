package com.monotoneid.eishms.services.databaseManagementSystem;

import com.monotoneid.eishms.dataPersistence.repositories.DeviceConsumptions;
import com.monotoneid.eishms.dataPersistence.repositories.HomeConsumptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class HomeConsumptionService{

    @Autowired
    private DeviceConsumptions deviceConsumptionsRepository;
    @Autowired
    private HomeConsumptions homeConsumptionsRepository;
    /**
     * Once per minute everyday
     */
    @Scheduled(cron = "*/10****")
    public void aggregateDeviceConsumption(){

    }

}