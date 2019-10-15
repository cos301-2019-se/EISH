package com.monotoneid.eishms.services.externalcommunicatons;

import java.sql.Timestamp;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.monotoneid.eishms.datapersistence.models.BatteryCapacity;
import com.monotoneid.eishms.datapersistence.models.ChargingStateType;
import com.monotoneid.eishms.datapersistence.models.PowerStateType;
import com.monotoneid.eishms.datapersistence.repositories.BatteryCapacities;
import com.monotoneid.eishms.exceptions.ResourceNotFoundException;
import com.monotoneid.eishms.services.databasemanagementsystem.NotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import net.minidev.json.JSONObject;

/**
 * CLASS BATTERY SERVICE.
 * Responsible for getting information related to the solar charged battery.
 */
@Service
@EnableScheduling
@EnableAsync
public class BatteryService {

    @Autowired
    private HttpConnection connection;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private BatteryCapacities batteryCapacityRepository;

    @Autowired
    private NotificationService notificationService;

    private String api = "http://127.0.0.1:3001/v2/installations/0/Battery";
    private boolean apiStatus = true;
    private boolean fullStatus = true;
    private boolean lowStatus = true;
    private boolean criticalStatus = true;
    private boolean emptyStatus = true;
    private final long rate = 15000;
    private final long delay = 10000;
    
    /**
     * This function is scheduled (at 15 seconds intervals) to:
     * save the current information of the battery in the database;
     * send the information through the socket (/battery).
     */
    @Async
    @Scheduled(fixedRate = rate, initialDelay = delay)
    public void getBatteryCapacity() {
        JSONObject batteryCapacity = new JSONObject();
        try {
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            StringBuffer content = connection.getContentFromURL(api);
            BatteryCapacity newBatteryCapacity;

            if (content == null) {
                newBatteryCapacity = new BatteryCapacity(
                  0, 0,PowerStateType.POWERSTATE_OFFLINE.toString(), 
                  ChargingStateType.CHARGINGSTATE_OFFLINE.toString(), currentTimestamp, 0);
                if (apiStatus == true) {
                    notifyBatteryStatus(PowerStateType.POWERSTATE_OFFLINE);    
                }
                apiStatus = false;
            } else {
                JsonObject jsonContent = new JsonParser().parse(content.toString())
                                                        .getAsJsonObject();
                String time = jsonContent.get("timestamp").getAsString();
                time = time.replace("T", " ");
                time = time.replace("Z",  "");
                currentTimestamp = Timestamp.valueOf(time);
                newBatteryCapacity = new BatteryCapacity(
                    jsonContent.get("storageCapacity").getAsInt(),
                    jsonContent.get("currentPower").getAsInt(),
                    jsonContent.get("powerState").getAsString(),
                    jsonContent.get("chargingState").getAsString(),
                    currentTimestamp,
                    jsonContent.get("powerPercentage").getAsInt()
                );
                System.out.println(newBatteryCapacity.getBatteryCapacityPowerState());
                System.out.println(newBatteryCapacity.getBatteryCapacityChargingState());
                batteryCapacityRepository.save(newBatteryCapacity);
                batteryCapacity.put("batteryCapacityPowerPercentage", 
                            newBatteryCapacity.getBatteryCapacityPowerPercentage());
                if (simpMessagingTemplate != null) {
                    simpMessagingTemplate.convertAndSend("/battery", batteryCapacity);
                }
                apiStatus = true;
                notifyBatteryStatus(newBatteryCapacity.getBatteryCapacityPowerState());
            }
        } catch (Exception e) {
            System.out.println("Could'nt get data from battery!");
            System.out.println("Error:  " + e.getMessage() + " " + e.getCause());
        }
    }

    /**
     * The function returns the last saved battery information from the database.
     * @return ResponseEntity<Object>
     */
    public ResponseEntity<Object> getLastBatteryLevel() {
        JSONObject batteryCapacity = new JSONObject();
        try {
            BatteryCapacity lastBatteryCapacity = batteryCapacityRepository.findLastBatteryLevel()
                                    .orElseThrow(() -> new ResourceNotFoundException("BatteryCapacity does not exist"));
            
            batteryCapacity.put("batteryCapacityPowerPercentage", 
                        lastBatteryCapacity.getBatteryCapacityPowerPercentage());
            return new ResponseEntity<>(batteryCapacity, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error: There is no Battery Level!");
            batteryCapacity.put("batteryCapacityPowerPercentage", 0);
            return new ResponseEntity<>(batteryCapacity, HttpStatus.OK);
        }
    }

    /**
     * The function sends a notification of the battery status through the socket (/notification).
     * @param powerState
     */
    private void notifyBatteryStatus(PowerStateType powerState) {
        if (simpMessagingTemplate != null) {
            JSONObject notificationObject = new  JSONObject();
            if (powerState == PowerStateType.POWERSTATE_FULL) {
                if (fullStatus == true) {
                    notificationObject.put("priority","PRIORITY_MINOR");
                    notificationObject.put("message","Battery Full!");
                    simpMessagingTemplate.convertAndSend("/notification", notificationObject);
                    fullStatus = false;
                    lowStatus = true;
                    criticalStatus = true;
                    emptyStatus = true;
                }
            } else if (powerState == PowerStateType.POWERSTATE_LOW) {
                if (lowStatus == true) {
                    notificationObject.put("priority","PRIORITY_WARNING");
                    notificationObject.put("message","WARNING: Battery Low!");
                    simpMessagingTemplate.convertAndSend("/notification", notificationObject);
                    fullStatus = true;
                    lowStatus = false;
                    criticalStatus = true;
                    emptyStatus = true;
                }
            } else if (powerState == PowerStateType.POWERSTATE_CRITICALLYLOW) {
                if(criticalStatus == true) {
                    notificationObject.put("priority","PRIORITY_CRITICAL");
                    notificationObject.put("message","CRITICAL: Battery Critically Low!");
                    simpMessagingTemplate.convertAndSend("/notification", notificationObject);
                    fullStatus = true;
                    lowStatus = true;
                    criticalStatus = false;
                    emptyStatus = true;
                }
            } else if (powerState == PowerStateType.POWERSTATE_EMPTY) {
                if(emptyStatus == true) {
                    notificationObject.put("priority","PRIORITY_CRITICAL");
                    notificationObject.put("message","CRITICAL: Battery Empty!");
                    simpMessagingTemplate.convertAndSend("/notification", notificationObject);
                    fullStatus = true;
                    lowStatus = true;
                    criticalStatus = true;
                    emptyStatus = false;
                }
            } else if (powerState == PowerStateType.POWERSTATE_OFFLINE) {
                notificationObject.put("priority","PRIORITY_CRITICAL");
                notificationObject.put("message","ERROR: Failure to connect to battery api!");
                simpMessagingTemplate.convertAndSend("/notification", notificationObject);
                fullStatus = true;
                lowStatus = true;
                criticalStatus = true;
                emptyStatus = true;
            }

            if (powerState != PowerStateType.POWERSTATE_NORMAL && notificationObject.getAsString("message") != null) {
                System.out.println("Message: " + notificationObject.getAsString("message") + " Priority: " + notificationObject.getAsString("priority") + " Time: " + new Timestamp(System.currentTimeMillis()));
                notificationService.addNotification(notificationObject.getAsString("message"),
                        notificationObject.getAsString("priority"),
                        new Timestamp(System.currentTimeMillis()));
            } else if (powerState == PowerStateType.POWERSTATE_NORMAL){
                fullStatus = true;
                lowStatus = true;
                criticalStatus = true;
                emptyStatus = true;
            }
        }
    }
}
