package com.monotoneid.eishms.integrationtests.controllertests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.monotoneid.eishms.datapersistence.models.Device;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class DeviceControllerTest extends AbstractTest {
    @Override
    @Before
    public void setUp() {
        super.setUp();
    }
    
    @Test
    public void retrieveAllDevicesTest() throws Exception {
        String uri = "/devices";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
            .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status =mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content =mvcResult.getResponse().getContentAsString();
        Device[] deviceList = super.mapFromJson(content, Device[].class);
        assertTrue(deviceList.length > 0);    
    }
}

