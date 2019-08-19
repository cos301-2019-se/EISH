package com.monotoneid.eishms.services.databaseManagementSystem;

import static com.monotoneid.eishms.datapersistence.models.NotificationStateType.NOTIFICATION_UNREAD;
import static com.monotoneid.eishms.datapersistence.models.NotificationStateType.NOTIFICATION_READ;

import java.sql.Timestamp;
import java.util.List;

import com.monotoneid.eishms.datapersistence.repositories.Notifications;
import com.monotoneid.eishms.exceptions.ResourceNotFoundException;
import com.monotoneid.eishms.datapersistence.models.Notification;
import com.monotoneid.eishms.datapersistence.models.NotificationStateType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import net.minidev.json.JSONObject;

@Service()
public class NotificationService {

    @Autowired
    private Notifications notificationsRepository;
    /**
    * Retrieves all the notifications in the database
    * @return List of all notifications
    */
   public List<Notification> retrieveAllNotifications(){
       return notificationsRepository.findAll();
   }

   public List<Notification> retrieveNotifactionsByState(String notificationStateToFind) {
     try{
         List<Notification> notificationList = null;
            
            if (notificationStateToFind.matches("NOTIFICATION_UNREAD")) {
                 System.out.println("within else State");
                 notificationList = notificationsRepository
                    .findByNotificationStateTypeUnread()
                    .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                    return notificationList;
            } else if (notificationStateToFind.matches("NOTIFICATION_READ")) {
                notificationList = notificationsRepository
                    .findByNotificationStateTypeRead()
                    .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                    return notificationList;
            }  else {
                throw null;
            }
            

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + "!");
            throw e;
        }
        
   }

   public boolean addNotification(String newNotificationDescription,
   String newNotificationPriorityType,
   String newNotificationStateType,
   Timestamp newNotificationTimeStamp) {
    if (!newNotificationDescription.isEmpty() && 
        !newNotificationPriorityType.isEmpty() &&
        !newNotificationStateType.isEmpty() &&
        !newNotificationTimeStamp.toString().isEmpty()) {
    Notification newNotification = new Notification(newNotificationDescription, 
            newNotificationPriorityType, 
            newNotificationStateType, 
            newNotificationTimeStamp);
    
        notificationsRepository.save(newNotification); 
        return true; 
    }
          return false;
   }

   public ResponseEntity<Object> updateNotificationState(long notificationId, String newNotificationState) {
       try {
           if (notificationId <= 0 || newNotificationState.isEmpty()) {
               throw null;
           }
           if ( newNotificationState.matches("NOTIFICATION_READ")) {
            Notification foundNotification = notificationsRepository.findById(notificationId)
            .orElseThrow(() -> new ResourceNotFoundException("Notification does not exist"));
            foundNotification.setNotificationStateType(NotificationStateType.valueOf("NOTIFICATION_READ"));
            notificationsRepository.save(foundNotification);

           } else {
               throw null;
           }
           JSONObject responseObject = new JSONObject();
            responseObject.put("message","Notification State updated!");
            return new ResponseEntity<>(responseObject,HttpStatus.OK);

       } catch (Exception e) {
        if (e.getCause() == null) {
            JSONObject responseObject = new JSONObject();
            responseObject.put("message","Error: Failed to update notification state!");
            return new ResponseEntity<>(responseObject,
                HttpStatus.PRECONDITION_FAILED);
        } else {
            JSONObject responseObject = new JSONObject();
            responseObject.put("message","Error: Failed to update notification state!");
            return new ResponseEntity<>(responseObject,
                HttpStatus.NOT_FOUND);
        }
       }
   }

}