package com.monotoneid.eishms.communications.controller;

import com.monotoneid.eishms.dataPersistence.models.HomeUserPresence;
import com.monotoneid.eishms.services.databaseManagementSystem.UserPresenceService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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


}