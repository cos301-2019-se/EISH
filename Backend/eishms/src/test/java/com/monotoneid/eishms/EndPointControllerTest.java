package com.monotoneid.eishms;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.monotoneid.eishms.controller.EndPointController;
import com.monotoneid.eishms.model.DeviceConsumption;
import com.monotoneid.eishms.model.DeviceRequestBody;
import com.monotoneid.eishms.model.Devices;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.beans.factory.annotation.Autowired;

@RunWith(SpringRunner.class)
@WebMvcTest(value = EndPointController.class, secure = false)
public class EndPointControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EndPointController endpointcontroller;
    @Autowired
    ObjectMapper mapper;
    String exampleDeviceJson ="{\"device_name\":\"Fridge\",\"topic\":\"sonoff-fridge\",\"min_watts\":100,\"max_watts\":200,\"device_type\":\"Fridge\",\"device_priority\": \"HIGH\",\"auto_start\": false}";
    String exampleDeviceConsumptionJson="{\"start_time\":7788748,\"end_time\":98899033}";

    @Test
    public void testViewDevices_GivenNoObject_shouldProduceArraywithNoObjects () throws Exception{
        ObjectNode objectNode = mapper.createObjectNode();
        //ObjectNode insideObjects = mapper.createObjectNode();
        ArrayNode arrayObjects = mapper.createArrayNode();
        //arrayObjects.add(insideObjects);
        //objectNode.put("data",arrayObjects); 

        Mockito.when(endpointcontroller.getDevices()).thenReturn(arrayObjects);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/view/devices")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse());

        String expected = "{data:[]}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }
     
    @Test
    public void testViewDevices_GivenObject_shouldProduceArrayListWithThisObject() throws Exception {
        ObjectNode objectNode = mapper.createObjectNode();
        ObjectNode insideObjects = mapper.createObjectNode();
        ArrayNode arrayObjects = mapper.createArrayNode();
        Devices mockDevice = new Devices("Fridge", "sonoff-fridge", 100, 200, "Fridge", 0, false, true);
        
                insideObjects.put("device_name", mockDevice.getDeviceName());
                insideObjects.put("device_type", mockDevice.getDeviceType());
                insideObjects.put("device_state", mockDevice.getDeviceState());
                arrayObjects.add(insideObjects);
                objectNode.put("data",arrayObjects); 

        Mockito.when(endpointcontroller.getDevices()).thenReturn(arrayObjects);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/view/devices")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse());

        String expected = "{data:[{device_name:Fridge,device_type:Fridge,device_state:true}]}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }
    
    @Test
    public void testAddDevices_GivenObject_ShouldProduceResponesWithNameOfNewDevice()throws Exception {
        ObjectNode objectNode = mapper.createObjectNode();
        ObjectNode insideObjects = mapper.createObjectNode();
        Devices mockDevice = new Devices("Fridge", "sonoff-fridge", 100, 200, "Fridge", 0, false, true);
        Mockito.when(endpointcontroller.addDevice(Mockito.any(DeviceRequestBody.class))).thenReturn(objectNode);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/add/device")
				.accept(MediaType.APPLICATION_JSON).content(exampleDeviceJson)
                .contentType(MediaType.APPLICATION_JSON);
        
                MvcResult result = mockMvc.perform(requestBuilder).andReturn();

                System.out.println(result.getResponse());
                String expected="{data:Fridge successfully inserted.}";
        
                //String expected = "{data: \""+mockDevice.getDeviceName()+ " successfully inserted.\"}";
        
                JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);        
                
               
    }/*
    @Test
    public void testViewDeviceConsumption_GivenObjectWithStartDateAndEndDate_ShouldProduceArrayWithDateAndConsumption() throws Exception{
        ObjectNode objectNode = mapper.createObjectNode();
        ObjectNode insideObjects = mapper.createObjectNode();
        Devices mockDevice = new Devices("Fridge", "sonoff-fridge", 100, 200, "Fridge", 0, false, true);
        DeviceConsumption newDeviceConsumption = new DeviceConsumption();
        newDeviceConsumption.setConsumption((float) 100.8);
        newDeviceConsumption.setConsumptionTime(1532148465);
        List<DeviceConsumption> dclist= new ArrayList<DeviceConsumption>();
        dclist.add(newDeviceConsumption);
        mockDevice.setDeviceConsumption(dclist);
        System.out.println("list " + mockDevice.getDeviceConsumption());
        Long  a =Mockito.anyLong();
        Mockito.when(endpointcontroller.viewDeviceConsumptionFromToTime(a,Mockito.anyLong(),Mockito.anyLong())).thenReturn(objectNode);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/api/view/device/consumption/"+a+"?start_date=15311484654&end_date=15321484700")
        .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse());

        //String expected = "{data:[{date_time:"+newDeviceConsumption.getTimeOfConsumption() +",consumption:"+ newDeviceConsumption.getConsumption()+" }]}";
        String expected ="{data:[]}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false); 
    }
    */
}





