package com.monotoneid.eishms.services.externalCommunicatons;

import java.sql.Timestamp;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.monotoneid.eishms.datapersistence.models.BatteryCapacity;
import com.monotoneid.eishms.datapersistence.repositories.BatteryCapacities;

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

    private String api = "http://localhost:6000/v2/installations/0/battery";
    private final long rate = 180000;
    private final long delay = 30000;
    
    /**
     * The funtion returns JSONobject with the current information of the battery.
     */
    //@Scheduled(fixedRate = rate, initialDelay = delay)
    public void getBatteryCapacity() {
        try {
            StringBuffer content = connection.getContentFromURL(api);
            JSONObject batteryCapacity = new JSONObject();
            BatteryCapacity newBatteryCapacity;

            if (content == null) {
                Timestamp currenTimestamp = new Timestamp(System.currentTimeMillis());
                newBatteryCapacity = new BatteryCapacity(
                    0, 0,"POWERSTATE_OFFLINE", "CHARGINGSTATE_OFFLINE", currenTimestamp, 0);
            } else {
                JsonObject jsonContent = new JsonParser().parse(content.toString())
                                                        .getAsJsonObject();

                newBatteryCapacity = new BatteryCapacity(
                    jsonContent.get("storageCapacity").getAsInt(),
                    jsonContent.get("currentPower").getAsInt(),
                    jsonContent.get("powerState").getAsString(),
                    jsonContent.get("chargingState").getAsString(),
                    Timestamp.valueOf(jsonContent.get("timestamp").getAsString()),
                    jsonContent.get("powerPercentage").getAsInt()
                );
            }

            batteryCapacity.put("batteryCapacityPowerPercentage", 
                            newBatteryCapacity.getBatteryCapacityPowerPercentage());
            simpMessagingTemplate.convertAndSend("/battery", batteryCapacity);
        } catch (Exception e) {
            System.out.println("Error:  " + e.getMessage() + " " + e.getCause());
            throw null;
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
            if (lastBatteryCapacity == null) {
                System.out.println("Error: There is no Battery Level!");
                batteryCapacity.put("batteryCapacityPowerPercentage", 5);
                return new ResponseEntity<>(batteryCapacity, HttpStatus.OK);
                //throw null;
            } else {
                batteryCapacity.put("batteryCapacityPowerPercentage", 
                            lastBatteryCapacity.getBatteryCapacityPowerPercentage());
                return new ResponseEntity<>(batteryCapacity, HttpStatus.OK);
            }
        } catch (Exception e) {
            System.out.println("Error: There is no Battery Level!");
            batteryCapacity.put("batteryCapacityPowerPercentage", 0);
            return new ResponseEntity<>(batteryCapacity, HttpStatus.OK);
            // System.out.println("Error:  " + e.getMessage() + " " + e.getCause());
            // throw null;
        }
    }
}