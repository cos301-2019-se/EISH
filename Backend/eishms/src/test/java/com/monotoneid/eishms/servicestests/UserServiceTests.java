package com.monotoneid.eishms.servicestests;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.monotoneid.eishms.dataPersistence.models.User;
import com.monotoneid.eishms.dataPersistence.repositories.Users;
import com.monotoneid.eishms.services.databaseManagementSystem.UserService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTests{
    @InjectMocks
    UserService userServiceTester;

    @Mock
    Users usersRepositoryTester;


    @Test
    public void testAddUserGivenObjectWithValidCredentials_ShouldRespondWithSuccessMessage()throws Exception{
        User newMockUser = new User("Eben","eben@labs.epiuse.com","12345","owntracks/eben/iPhone/house");
        userServiceTester.addUser(newMockUser);
        verify(usersRepositoryTester, times(1)).save(newMockUser);

    } 
    public void testAddUserGivenObjectWithInavlidEmptyUserName_ShouldRespondWithfailMessage()throws Exception{
        User newMockUser = new User("","eben@labs.epiuse.com","12345","owntracks/eben/iPhone/house");
        userServiceTester.addUser(newMockUser);
        verify(usersRepositoryTester, times(0)).save(newMockUser);
    }
    public void testAddUserGivenObjectWithUserPassword_ShouldRespondWithfailMessage()throws Exception{

    }
    public void testAddUserGivenObjectWithUserEmail_ShouldRespondWithfailMessage()throws Exception{

    }
    public void testAddUserGivenObjectWithUserLocationTopic_ShouldRespondWithfailMessage()throws Exception{

    }


    
    
}
