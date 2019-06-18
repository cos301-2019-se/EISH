package com.monotoneid.eishms.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus() 
public class DeviceConsumptionDoesNotExistException extends RuntimeException{

  public DeviceConsumptionDoesNotExistException(long device_id){
    super("device_id "+device_id + " does not exist.");
  }
}