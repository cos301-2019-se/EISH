package com.monotoneid.eishms.unittests.endpointcontrollertests;

import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.swing.text.AbstractDocument.Content;

import com.monotoneid.eishms.communications.controller.DeviceEndPointController;
import com.monotoneid.eishms.services.databasemanagementsystem.DeviceService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@WebMvcTest(DeviceEndPointController.class)
public class EndPointControllerIntegrationTest {

    

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeviceService deviceService; 

    @Test
    public void shouldReturnListOfDevices() throws Exception {
        when(deviceService.retrieveAllDevices()).thenReturn(anyList());
        this.mockMvc.perform(get("/api/devices")).andDo(print()).andExpect(status().isOk());
           // .andExpect(content().json(jsonContent));
        //find out about request builders    
    }
    


}
