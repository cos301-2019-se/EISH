package com.monotoneid.eishms;

import com.monotoneid.eishms.datapersistence.models.HomeUser;
import com.monotoneid.eishms.datapersistence.repositories.Users;
import com.monotoneid.eishms.services.databaseManagementSystem.UserService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EishmsApplicationTests {
	
	private UserService userService;

    @MockBean
	private Users usersRepository;


	@Test
	public void contextLoads() {
	}

}
