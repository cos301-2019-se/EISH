package com.monotoneid.eishms.servicestests;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.monotoneid.eishms.dataPersistence.models.HomeUser;
import com.monotoneid.eishms.dataPersistence.repositories.Users;
import com.monotoneid.eishms.services.databaseManagementSystem.UserService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTests{
    @InjectMocks
    UserService userServiceTester;

    @Mock
    PasswordEncoder encoder;

    @Mock
    Users usersRepositoryTester;

    //Get Users
    @Test
    public void testRetrieveAllUsersGiventhatListHasNoUsers_ShouldRespondWithEmptyList()throws Exception{

    }
    @Test
    public void testRetriveAllUsersGivenThataListHasOneUser_ShouldRespondWithListWithOneItem()throws Exception{

    }
    @Test
    public void testRetrieveAllUsersGiventhatListHasMoreThanOneUsers_ShouldRespondWithListGreaterthanOne()throws Exception{

    }
    

    @Test
    public void testAddUserGivenObjectWithValidCredentials_ShouldRespondWithSuccessMessage()throws Exception{
        HomeUser newMockUser = new HomeUser("Eben","eben@labs.epiuse.com","12345","owntracks/eben/iPhone/house");
        userServiceTester.addUser(newMockUser);
        verify(usersRepositoryTester, times(1)).save(newMockUser);

    } 
    @Test
    public void testAddUserGivenObjectWithInavlidEmptyUserName_ShouldRespondWithfailMessage()throws Exception{
        HomeUser newMockUser2 = new HomeUser("","eben@labs.epiuse.com","12345","owntracks/eben/iPhone/house");
        userServiceTester.addUser(newMockUser2);
        verify(usersRepositoryTester, times(0)).save(newMockUser2);
    }
    
   
    @Test
    public void testAddUserGivenObjectWithInvalidEmptyUserPassword_ShouldRespondWithfailMessage()throws Exception{
        HomeUser newMockUser = new HomeUser("Eben","eben@labs.epiuse.com","","owntracks/eben/iPhone/house");
        userServiceTester.addUser(newMockUser);
        verify(usersRepositoryTester, times(0)).save(newMockUser);
    }
    @Test
    public void testAddUserGivenObjectWithInvalidEmptyUserEmail_ShouldRespondWithfailMessage()throws Exception{
        HomeUser newMockUser = new HomeUser("Eben","","12345","owntracks/eben/iPhone/house");
        userServiceTester.addUser(newMockUser);
        verify(usersRepositoryTester, times(0)).save(newMockUser);
    }
    @Test
    public void testAddUserGivenObjectWithInvalidEmptyUserLocationTopic_ShouldRespondWithfailMessage()throws Exception{
        HomeUser newMockUser = new HomeUser("Eben","eben@labs.epiuse.com","12345","");
        userServiceTester.addUser(newMockUser);
        verify(usersRepositoryTester, times(0)).save(newMockUser);
    }
    @Test
    public void testAddUserGivenObjectWithInvalidEmptyCredentials_ShouldRespondWithfailMessage()throws Exception{
        HomeUser newMockUser = new HomeUser("","","","");
        userServiceTester.addUser(newMockUser);
        verify(usersRepositoryTester, times(0)).save(newMockUser);
    }

    @Test
    public void testUpdateUserEmailGivenObjectWithValidEmail_ShouldRespondWithSucessMessage() throws Exception{
        //String newEmailAddress="admin@eishms.co.za";
        Long userIdValue= (long) 1;
        System.out.println("test");
        HomeUser currentMockUser =new HomeUser("Eben","eben@labs.epiuse.com","12345","owntracks/eben/iPhone/house");
        System.out.println("test2");
        userServiceTester.updateUser(userIdValue,currentMockUser);
        System.out.println("test3");
        verify(usersRepositoryTester,times(1)).save(currentMockUser);
        System.out.println("test4");
    }


    
    
}
