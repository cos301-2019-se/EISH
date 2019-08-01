package com.monotoneid.eishms.communications.controller;

import com.monotoneid.eishms.datapersistence.models.HomeUserPresence;
import com.monotoneid.eishms.services.databaseManagementSystem.UserPresenceService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserPresenceEndPointController {

    @Autowired
    private UserPresenceService userPresenceService;

    /**
    * GET METHOD
    * Implements retrieveUserPresence endpoint, that calls the retrieveUserPresence service
    * @param userId
    * @return a the valid UserPresenceList
    */
     
    @GetMapping(value = "/presence", params = {"userId","startTimeStamp","endTimeStamp"})
    @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
    public List<HomeUserPresence> retrieveHomeUserPresenceCases(
        @RequestParam(value = "userId", required = true) long userId,
        @RequestParam(value = "startTimeStamp", required = true) String startTimeStamp,
        @RequestParam(value = "endTimeStamp", required = true) String endTimeStamp) {
        return userPresenceService.retrieveAllHomeUserPresenceCases(userId, startTimeStamp, endTimeStamp);
    }

    @GetMapping(value = "/presence", params = {"userId","interval"})
    @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
    public List<HomeUserPresence> retrieveHomeUserPresenceBetweenInterval(
        @RequestParam(value = "userId", required = true) long userId,
        @RequestParam(value = "interval", required = true) String interval) {
        return userPresenceService.retrieveBetweenInterval(userId, interval);
    }

    /**
    * GET METHOD.
    * Implements getUserPresence endpoint, that calls the getUserPresence service
    * @param userId represents the user id
    * @return an object with the presence of the user
    */
    @GetMapping(value = "/presence", params = {"userId"})
    @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
    public ResponseEntity<Object> getUserPresence(
        @RequestParam(value = "userId", required = true) long userId) {
        return userPresenceService.getCurrentUserPresence(userId);
    }

    @GetMapping(value = "/presences")
    @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
    public List<HomeUserPresence> retrieveAllPresentUsers() {
        return userPresenceService.findHomeUsersThatArePresent();
    }
}