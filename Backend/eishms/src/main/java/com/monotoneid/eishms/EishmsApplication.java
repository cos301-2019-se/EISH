package com.monotoneid.eishms;

import com.monotoneid.eishms.dataPersistence.repositories.HomeKeys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EishmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EishmsApplication.class, args);
	}

}
