package com.monotoneid.eishms.services.externalCommunicatons;

import java.sql.Timestamp;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.monotoneid.eishms.datapersistence.models.Generator;
import com.monotoneid.eishms.datapersistence.models.GeneratorGeneration;
import com.monotoneid.eishms.services.databaseManagementSystem.GeneratorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import net.minidev.json.JSONObject;

/**
 * CLASS GENERATION SERVICE.
 */
@Service
public class GenerationService {
    
    @Autowired
    private HttpConnection connection;

    @Autowired
    private GeneratorService generatorService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    //private String apiCurrent = "http://localhost:6000/v2/installations/0/current";
    //private String apiAll = "http://localhost:6000/v2/installations/0/all";
    private final long rate = 900000;
    private final long delay = 60000;

    /**
     * .
     */
    //@Scheduled(fixedRate = rate, initialDelay = delay)
    public void getCurrentGeneration() {
        try {
            List<Generator> generators = generatorService.retrieveAllGenerators();
            JSONObject generation = new JSONObject();
            float generationValue = 0;
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            for (Generator generator : generators) {
                StringBuffer content = connection.getContentFromURL(generator.getGeneratorUrl());
                GeneratorGeneration newGeneratorGeneration;

                if (content == null) {
                    newGeneratorGeneration = new GeneratorGeneration(
                        0, generator, currentTimestamp, "OFFLINE");
                    generationValue += newGeneratorGeneration.getGeneratorGeneration();
                } else {
                    JsonObject jsonContent = new JsonParser().parse(content.toString())
                                                            .getAsJsonObject();
                    newGeneratorGeneration = new GeneratorGeneration(
                        jsonContent.get("generation").getAsFloat(),
                        generator,
                        Timestamp.valueOf(jsonContent.get("timestamp").getAsString()),
                        "ON"
                    );
                    generationValue += newGeneratorGeneration.getGeneratorGeneration();
                }
            }
            generation.put("generationTimestamp", currentTimestamp);
            generation.put("generationValue", generationValue);
            simpMessagingTemplate.convertAndSend("/generation", generation);
        } catch (Exception e) {
            System.out.println("Error:  " + e.getMessage() + " " + e.getCause());
            throw null;
        }
    }
}