package com.monotoneid.eishms.communications.controller;

import com.monotoneid.eishms.datapersistence.models.HomeKey;
import com.monotoneid.eishms.datapersistence.models.HomeUser;
import com.monotoneid.eishms.datapersistence.repositories.HomeKeys;
import com.monotoneid.eishms.services.databaseManagementSystem.UserService;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *CLASS USER END POINT CONTROLLER. 
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController()
@RequestMapping("/api")
public class UserEndPointController {
    @Autowired
    private UserService userService;

    @Autowired
    private HomeKeys myHouseKeys;

    /**
    * GET METHOD.
    * Implements the getKeys() endpoint,
     used for the admin to retrieve the GENERAL and RENEWAL keys.
    * @return keyList
    */
    @GetMapping("/keys")
    @PreAuthorize("hasRole('ADMIN')") 
    public List<HomeKey> getKeys() {
        List<HomeKey> keyList = new ArrayList<HomeKey>();
        keyList.add(myHouseKeys.findByKeyName("general"));
        keyList.add(myHouseKeys.findByKeyName("renewal"));
        return keyList;
    }

    /**
    * POST METHOD.
    * Implements the addUser endpoint, that calls the addUser service
    * @param newHomeUser used to create home user details
    * @return the status message
    */
    @PostMapping("/user")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GENERAL')")
    public ResponseEntity<Object> addUser(@Valid @RequestBody HomeUser newHomeUser) {
        return userService.addUser(newHomeUser);
    }

    /**
    * GET METHOD.
    * Implements retrieveUser endpoint, that calls the retrieveUser service
    * @param homeUserName represents the name of the user
    * @return a the valid homeUser
    */
    @GetMapping(value = "/user",params = {"userName"})
    @PreAuthorize("hasRole('ADMIN') or hasRole('RESIDENT') or hasRole('GUEST')")
    public ResponseEntity<HomeUser> retriveUser(
        @Valid @RequestParam(value = "userName") String homeUserName) {
        return userService.retrieveUser(homeUserName);
    }

    /**
     * GET METHOD.
     * Implements retrieveAllUsers endpoint, that calls the retrieveAllUsers service
     * @return an object with all users 
     */
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<HomeUser> retriveAllUsers() {
        return userService.retrieveAllUsers();
    }

    /**
    * Implements updateUser endpoint, that calls the updateUser service.
    * @param homeUser represent new user details
    * @return object message
    */
    @PutMapping("/user")
    @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody HomeUser homeUser) {
        return userService.updateUser(homeUser);
    }

    /**
    * DELETE METHOD.
    * Implements removeUser endpoint, that calls the removeUser service
    * @param userId represents the id of user
    * @return an object with all the remaining users
    */
    @DeleteMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
   public ResponseEntity<Object> removeUser(@PathVariable long userId) {
        return userService.removeUser(userId);
    }

    /**
    * PATCH METHOD.
    * Implements updateUserType endpoint, that calls the updateUserType service
    * @param userId represents the users id
    * @return Object message
    */
    @PatchMapping("/user/usertype/{userId}/{userType}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> updateUserType(
        @PathVariable long userId, @PathVariable String userType) {
        return userService.updateUserType(userId,userType);
    }
  
    /**
    * update user expirydate.
    * @param userId represents the users id
    * @return object message
    */
    @PatchMapping("/user/expiration/{userId}/{numDays}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RENEWAL')")
    public ResponseEntity<Object> renewUser(@PathVariable long userId, @PathVariable int numDays) {
        return userService.renewUser(userId,numDays);
    }
}
