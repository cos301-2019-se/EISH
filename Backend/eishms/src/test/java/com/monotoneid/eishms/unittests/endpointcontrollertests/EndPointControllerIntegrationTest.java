package com.monotoneid.eishms.unittests.endpointcontrollertests;

//import static org.junit.Assert.assertTrue;

// import java.util.Optional;

// import com.monotoneid.eishms.dataPersistence.models.HomeUser;
// import com.monotoneid.eishms.dataPersistence.repositories.Users;
// import com.monotoneid.eishms.services.databaseManagementSystem.UserService;

// import org.junit.Test;
import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.boot.test.web.client.TestRestTemplate;
// import org.springframework.boot.web.server.LocalServerPort;
// import org.springframework.http.HttpEntity;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.reactive.server.WebTestClient;
//import org.springframework.test.web.servlet.MockMvc;

//@RunWith(SpringRunner.class)
// @WebMvcTest(EndPointController.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
public class EndPointControllerIntegrationTest {

    //@Autowired
    //private WebTestClient webClient;
    
    //@Test
    //public void exampleTest() {
      //  this.webClient.get().uri("/users").exchange().expectStatus().isOk();
       // .expectBody(String.class).isEqualTo();
    //}

    //@Autowired
   // private MockMvc mvc;
    //@Test
    //public void testCreateRetrieveWithMockMVC() throws Exception {
       // this.mvc.perform(post("/user")).expectStatus().is2xxSuccessful();
    //}

    // @Autowired
    // private UserService service;
    
    // @Autowired
    // private Users usersRepository;

    // @Test
    // public void testRetrieveUser() throws Exception {
      //  Optional<HomeUser> optUser = Optional.of(new HomeUser("Eben","eben@labs.epiuse.com","12345","owntracks/eben/iPhone/house"));
       // when(usersRepository.findById(1)).thenReturn(optUser);

        //assertTrue(service.retrieveUser(1).getUserName().contains("Eben"));
    //}
    
    
    // @LocalServerPort
    // private int port;
    
    // TestRestTemplate restTemplate = new TestRestTemplate();

    // HttpHeaders headers = new HttpHeaders();

    // /**
    //  * POST METHOD Tests the addUser endpoint
    //  */
    // @Test
    // public void testgivenUserValidCredentials_whenPostUser_thenReturnObjectWithString() throws Exception {
    //     HttpEntity<String> entity = new HttpEntity<String>(null, headers);

    //     ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/user"), HttpMethod.POST, entity, String.class);

    //     String actual = response.getHeaders().get(HttpHeaders.LOCATION).get(0);

    //     assertTrue(actual.contains("/users"));
    // }

    // private String createURLWithPort(String uri) {
    //     return "http://localhost:" + port + uri;
    // }

    /**
     * GET METHOD Tests the retrieveAllUsers endpoint
     */
  //  @Test
    //public void givenUsers_whenGetUsers_thenReturnJsonArray() throws Exception {
    /*    UserRequestBody urb = new UserRequestBody();
        HomeUser newMockUser = new HomeUser();

        List<HomeUser> allHomeUsers = Arrays.asList(newMockUser);

        given(service.retriveAllUsers()).willReturn(allHomeUsers);

        mvc.perform(get("api/users")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(JsonPath("$[0].userName", Is(newMockUser.getUserName())));
        */
    //}
}