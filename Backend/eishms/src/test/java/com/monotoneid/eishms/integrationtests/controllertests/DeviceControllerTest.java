package com.monotoneid.eishms.integrationtests.controllertests;

import static org.junit.Assert.assertTrue;

import com.monotoneid.eishms.communications.controller.DeviceEndPointController;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(value = DeviceEndPointController.class, secure = false)

public class DeviceControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DeviceEndPointController deviceEndPointController;

}

