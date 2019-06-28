package com.monotoneid.eishms.dataPersistence.models;

public class Device {
    private long deviceId;
    private String deviceName;
    private String deviceTopic;
    private DeviceType deviceType;
    private DevicePriorityType devicePriority;

    public long getDeviceId(){
        return deviceId;
    }

    public String getDeviceName(){
        return deviceName;
    }

    public String getDeviceTopic(){
        return deviceTopic;
    }
    public DeviceType getDeviceType(){
        return deviceType;
    }
    public DevicePriorityType getDevicePriority(){
        return devicePriority;
    }


}