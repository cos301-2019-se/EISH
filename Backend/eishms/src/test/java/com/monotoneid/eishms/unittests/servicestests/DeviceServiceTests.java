package com.monotoneid.eishms.unittests.servicestests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.monotoneid.eishms.datapersistence.models.Device;
import com.monotoneid.eishms.datapersistence.models.DeviceConsumption;
import com.monotoneid.eishms.datapersistence.models.DevicePriorityType;
import com.monotoneid.eishms.datapersistence.repositories.Devices;
import com.monotoneid.eishms.services.databasemanagementsystem.DeviceService;

import org.hamcrest.core.Is;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DeviceServiceTests{
   
    @Mock
    Devices deviceRepository;

    @InjectMocks
    DeviceService deviceService;

    @Test
    public void whenRetrieveAllDevicesEmptyList_returnEmptyList() {
        List<Device> mockDeviceList = new ArrayList<>();
        when(deviceRepository.save(any(Device.class))).thenReturn(new Device());
        List<Device> foundDevices = deviceService.retrieveAllDevices();
        assertEquals(foundDevices.size(),0);
    }

    @Test
    public void whenRetrieveAllDevices_returnListOfDevices() {
     //create a list inject 
     Device mockDevice = new Device();
     mockDevice.setDeviceName("Laptop Charger");
     mockDevice.setDeviceTopic("sonoff-charger");
     List<DeviceConsumption> mockDeviceConsumption = new ArrayList<>();
     mockDevice.setDeviceConsumption(mockDeviceConsumption);
     mockDevice.setDevicePriorityType(DevicePriorityType.PRIORITY_NEUTRAL);
     String[] mockDeviceStates = {"OFFLINE","OFF","ON"};
     mockDevice.setDeviceStates(mockDeviceStates);

     List<Device> mockDeviceList = new ArrayList<>();
     mockDeviceList.add(mockDevice);

     when(deviceRepository.save(any(Device.class))).thenReturn(new Device());
     //call function
     List<Device> foundDevices = deviceService.retrieveAllDevices();
     System.out.println(foundDevices.size());
     //assert result
     //assertThat(foundDevices.size(),Is(1));//"only one device found in repository"); 
     assertEquals(foundDevices.size(),1);  
    }
}