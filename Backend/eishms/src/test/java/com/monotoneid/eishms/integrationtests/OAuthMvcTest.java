package com.monotoneid.eishms.integrationtests;
import com.monotoneid.eishms.configuration.JwtAuthenticationEntryPoint;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import ch.qos.logback.core.status.Status;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = JwtAuthenticationEntryPoint.class)
public class OAuthMvcTest {
 
    @Autowired
    private WebApplicationContext webApplicationContext;
 
    @Autowired
    private FilterChainProxy springSecurityFilterChain;
 
    private MockMvc mockMvc;
 
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
          .addFilter(springSecurityFilterChain).build();
    }

   // private String obtainAccessToken(String username, String password) throws Exception {
  
     //   MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        //params.add("grant_type", "password");
        //params.add("client_id", "fooClientIdPassword");
       // params.add("username", username);
       // params.add("password", password);
     
        // ResultActions result 
        //   = mockMvc.perform(post("/api/auth/login")
        //     .params(params)
        //     .with(httpBasic("fooClientIdPassword","secret"))
        //     .accept("application/json;charset=UTF-8"))
        //     .andExpect(Status().isOk())
        //     .andExpect(content().contentType("application/json;charset=UTF-8"));
     
        // String resultString = result.andReturn().getResponse().getContentAsString();
     
        // JacksonJsonParser jsonParser = new JacksonJsonParser();
        // return jsonParser.parseMap(resultString).get("access_token").toString();
    //}

}