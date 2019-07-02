package com.monotoneid.eishms.dataPersistence.requestbodies;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceRequestBody {

    
    private long deviceId;
    private String deviceName;
    private String deviceTopic;
    private String devicePriorityType;
    private Long deviceTypeId;

    public DeviceRequestBody() {}

    public DeviceRequestBody(@JsonProperty("deviceId") long dId,
        @JsonProperty("deviceName") String dName,
        @JsonProperty("deviceTopic") String dTopic,
        @JsonProperty("devicePriorityType") String dPriority,
        @JsonProperty("deviceTypeId") Long dType) {
            this.deviceId = dId;
            this.deviceName = dName;
            this.deviceTopic = dTopic;
            this.devicePriorityType = dPriority;
            this.deviceTypeId = dType;
    }
    
    public long getDeviceID() {
        return this.deviceId;
    }
    
    public String getDeviceName() {
        return this.deviceName;
    }

    public String getDeviceTopic() {
        return this.deviceTopic;
    }

    public String getDevicePriority(){
        return this.devicePriorityType;
    }

    public Long getDeviceTypeId() {
        return this.deviceTypeId;
    }
}