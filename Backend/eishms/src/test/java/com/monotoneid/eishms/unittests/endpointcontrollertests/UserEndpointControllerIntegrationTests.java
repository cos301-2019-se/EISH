package com.monotoneid.eishms.unittests.endpointcontrollertests;

import com.monotoneid.eishms.communications.controller.UserEndPointController;
import com.monotoneid.eishms.datapersistence.repositories.Users;
import com.monotoneid.eishms.services.databasemanagementsystem.UserService;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;



@RunWith(SpringRunner.class)
@WebMvcTest(UserEndPointController.class)
public class UserEndpointControllerIntegrationTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    public void getKeysShouldReturnHomeKeys() throws Exception {
        this.mvc.perform(get("/forTest")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("ForTest")));
    }

}