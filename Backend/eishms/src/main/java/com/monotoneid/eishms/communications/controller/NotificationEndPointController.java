package com.monotoneid.eishms.communications.controller;

import java.util.List;

import com.monotoneid.eishms.datapersistence.models.Notification;
import com.monotoneid.eishms.services.databasemanagementsystem.NotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *CLASS NOTIFICATION ENDPOINT CONTROLLER. 
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class NotificationEndPointController {

    @Autowired
    private NotificationService notificationService;

    /**
     * GET METHOD.
     * Implements retrieveAllNotification endpoint, that calls the retrieveAllNotification service
     * @return an object with all notifications 
     */
    @GetMapping(value = "/notifications")
    @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
    public List<Notification> retrieveAllNotifications() {
        return notificationService.retrieveAllNotifications();
    }

    @GetMapping(value = "/notification", params = {"notificationState"})
    @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
    public List<Notification> retrieveNotificationCases(
        @RequestParam(value = "notificationState", required = true) String notificationState ) {
            return notificationService.retrieveNotifactionsByState(notificationState);
    }

    /**
    * PATCH METHOD.
    * Implements updatesNotificationState endpoint, that calls the updateNotification service
    * @param notificationId represents the notification id
    * @return Object message
    */
    @PatchMapping(value = "/notification/notificationstate/{notificationId}/{notificationState}")
    @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
    public ResponseEntity<Object> updateNotificationState(
        @PathVariable long notificationId, @PathVariable String notificationState) {
            return notificationService.updateNotificationState(notificationId, notificationState);
    }

}