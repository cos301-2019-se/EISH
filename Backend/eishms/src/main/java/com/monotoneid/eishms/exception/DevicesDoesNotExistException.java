package com.monotoneid.eishms.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus() 
public class DevicesDoesNotExistException extends RuntimeException{

  public DevicesDoesNotExistException(long device_id){
    super("device_id "+device_id + " does not exist.");
  }
}