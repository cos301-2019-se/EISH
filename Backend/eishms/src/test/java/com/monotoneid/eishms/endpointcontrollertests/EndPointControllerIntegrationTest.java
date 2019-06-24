package com.monotoneid.eishms.endpointcontrollertests;

import java.util.Arrays;

import javax.xml.ws.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jayway.jsonpath.JsonPath;
import com.monotoneid.eishms.communications.controller.EndPointController;
import com.monotoneid.eishms.dataPersistence.models.HomeUser;
import com.monotoneid.eishms.dataPersistence.repositories.Users;
import com.monotoneid.eishms.services.databaseManagementSystem.UserService;

import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(EndPointController.class)
@SpringBootTest
public class EndPointControllerIntegrationTest {
    
    /*

    @Autowired
    private MockMvc mvc;

    @MockBean
    private Users usersRepository;

    @MockBean
    private UserService service;
    
    /**
     * POST METHOD
     * Tests the addUser endpoint
    */
    @Test
    public void testgivenUserValidCredentials_whenPostUser_thenReturnObjectWithString() throws Exception {      
        /*HomeUser newMockUser = new HomeUser("Eben","eben@labs.epiuse.com","12345","Towntracks/eben/iPhone/house");

        given(service.addUser(newMockUser)).willReturn(String);

        mvc.perform(post("api/user")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("data", is(objectNode.findValue("data"))));
    */}

    /**
     * GET METHOD
     * Tests the retrieveAllUsers endpoint
     */
    @Test
    public void givenUsers_whenGetUsers_thenReturnJsonArray() throws Exception {
    /*    UserRequestBody urb = new UserRequestBody();
        HomeUser newMockUser = new HomeUser();

        List<HomeUser> allHomeUsers = Arrays.asList(newMockUser);

        given(service.retriveAllUsers()).willReturn(allHomeUsers);

        mvc.perform(get("api/users")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(JsonPath("$[0].userName", Is(newMockUser.getUserName())));
        */
    }
}