package com.monotoneid.eishms;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


import com.monotoneid.eishms.model.DeviceConsumption;
import com.monotoneid.eishms.model.Devices;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;




import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import org.springframework.web.client.HttpClientErrorException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EishmsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class EishmsApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}
	@Test
	public void isGettingDevicesAfterQueryShouldReturnDevices() {
	}
	@Test
	public void testGetAllDevices() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/api/view/devices",
				HttpMethod.GET, entity, String.class);

		Assert.assertNotNull(response.getBody());
	}
	@Test
	public void testGetAllGenerators(){
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/api/view/generators",
				HttpMethod.GET, entity, String.class);

		Assert.assertNotNull(response.getBody());

	}
	@Test
	public void testAddDevice() {
		Devices newDevice = new Devices();
		newDevice.setDeviceName("Fridge");
		newDevice.setDeviceState(false);
		newDevice.setDeviceType("fridge");
		newDevice.setDeviceMaxWatt(200);
		newDevice.setDeviceMinWatt(130);
		newDevice.setDeviceAutoStart(false);
		newDevice.setDevicePriority(1);
		newDevice.setDeviceTopic("/Fridge");
		List<DeviceConsumption> dclist= new ArrayList<DeviceConsumption>();
		//DeviceConsumption dc;
		//dc.setConsumption(150);
		//dclist.add(dc);
		newDevice.setDeviceConsumption(dclist);

		ResponseEntity<Devices> postResponse = restTemplate.postForEntity(getRootUrl() + "/add/device", newDevice, Devices.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());
	}
}
