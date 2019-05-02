package com.monotoneid.eishms;

import java.util.List;

import com.monotoneid.eishms.model.Device;
import com.monotoneid.eishms.repository.DevicesRepository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EishmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EishmsApplication.class, args);
		//DevicesRepository devicesRepository;
		//List<Device> allDevices = devicesRepository.findAll();
	}

}
