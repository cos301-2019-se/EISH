package com.monotoneid.eishms.services.externalCommunicatons;

import java.sql.Timestamp;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.monotoneid.eishms.datapersistence.models.BatteryCapacity;
import com.monotoneid.eishms.datapersistence.repositories.BatteryCapacities;
import com.monotoneid.eishms.exceptions.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import net.minidev.json.JSONObject;

/**
 * CLASS BATTERY SERVICE.
 */
@Service
public class BatteryService {

    @Autowired
    private HttpConnection connection;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private BatteryCapacities batteryCapacityRepository;

    private String api = "http://192.168.8.101:3001/v2/installations/0/Battery";
    private final long rate = 180000;
    private final long delay = 30000;
    
    /**
     * The funtion returns JSONobject with the current information of the battery.
     */
    @Scheduled(fixedRate = rate, initialDelay = delay)
    public void getBatteryCapacity() {
        try {
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            StringBuffer content = connection.getContentFromURL(api);
            JSONObject batteryCapacity = new JSONObject();
            BatteryCapacity newBatteryCapacity;

            if (content == null) {
                System.out.println("Content from api is null!");
                newBatteryCapacity = new BatteryCapacity(
                    0, 0,"POWERSTATE_OFFLINE", "CHARGINGSTATE_OFFLINE", currentTimestamp, 0);
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
            }
            batteryCapacityRepository.save(newBatteryCapacity);
            batteryCapacity.put("batteryCapacityPowerPercentage", 
                            newBatteryCapacity.getBatteryCapacityPowerPercentage());
            System.out.println("Published battery power percentage at " + currentTimestamp);
            simpMessagingTemplate.convertAndSend("/battery", batteryCapacity);
        } catch (Exception e) {
            System.out.println("Error:  " + e.getMessage() + " " + e.getCause());
        }
    }

    /**
     * .
     * @return
     */
    public ResponseEntity<Object> getLastBatteryLevel() {
        JSONObject batteryCapacity = new JSONObject();
        try {
            BatteryCapacity lastBatteryCapacity = batteryCapacityRepository.findLastBatteryLevel()
                                    .orElseThrow(() -> new ResourceNotFoundException("BatteryCapacity does not exist"));
            
            batteryCapacity.put("batteryCapacityPowerPercentage", 
                        lastBatteryCapacity.getBatteryCapacityPowerPercentage());
            System.out.println("Last battery capacity: " + batteryCapacity);
            return new ResponseEntity<>(batteryCapacity, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error: There is no Battery Level!");
            batteryCapacity.put("batteryCapacityPowerPercentage", 0);
            return new ResponseEntity<>(batteryCapacity, HttpStatus.OK);
        }
    }
}