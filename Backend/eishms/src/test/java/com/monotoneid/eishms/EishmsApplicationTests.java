package com.monotoneid.eishms;

import java.util.Collections;

import com.monotoneid.eishms.repository.DevicesRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
//@SpringBootTest
@WebMvcTest
public class EishmsApplicationTests {

	@Autowired
	
	MockMvc mockMvc;

	@MockBean
	DevicesRepository devicesRepository;
	
	@Test
	public void contextLoads() throws Exception {
		
		Mockito.when(devicesRepository.findAll()).thenReturn(
			Collections.emptyList()
		);

		MvcResult mvcResult = mockMvc.perform(
			MockMvcRequestBuilders.get("api/devices")
			.accept(MediaType.APPLICATION_JSON)
		).andReturn();

		System.out.println(mvcResult.getResponse());

		Mockito.verify(devicesRepository).findAll();
	}
}
