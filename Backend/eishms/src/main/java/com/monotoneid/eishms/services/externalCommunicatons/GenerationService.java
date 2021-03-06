package com.monotoneid.eishms.services.externalcommunicatons;

import java.sql.Timestamp;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.monotoneid.eishms.datapersistence.models.Generator;
import com.monotoneid.eishms.datapersistence.models.GeneratorGeneration;
import com.monotoneid.eishms.datapersistence.repositories.GeneratorGenerations;
import com.monotoneid.eishms.services.databasemanagementsystem.GeneratorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import net.minidev.json.JSONObject;

/**
 * CLASS GENERATION SERVICE.
 */
@Service
@EnableScheduling
@EnableAsync
public class GenerationService {
    
    @Autowired
    private HttpConnection connection;

    @Autowired
    private GeneratorService generatorService;

    @Autowired
    private GeneratorGenerations generationRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    private String apiCurrent = "http://127.0.0.1:3001/v2/installations/0/SolarCharger/current";
    //private String apiAll = "http://localhost:6000/v2/installations/0/all";
    private final long rate = 20000;
    private final long delay = 20000;

    /**
     * .
     */
    @Async
    @Scheduled(fixedRate = rate, initialDelay = delay)
    public void getCurrentGeneration() {
        try {
            List<Generator> generators = generatorService.retrieveAllGenerators();
            JSONObject generation = new JSONObject();
            float generationValue = 0;
            Timestamp currentTimestamp1 = new Timestamp(System.currentTimeMillis());
            for (Generator generator : generators) {
                //StringBuffer content = connection.getContentFromURL
                //(generator.getGeneratorUrl() + "/current");
                StringBuffer content = connection.getContentFromURL(apiCurrent);
                GeneratorGeneration newGeneratorGeneration;
                // System.out.println("Return from the HttpConnection");
                if (content == null) {
                    newGeneratorGeneration = new GeneratorGeneration(
                        0, generator, currentTimestamp1, "OFFLINE");
                    generationValue += newGeneratorGeneration.getGeneratorGeneration();
                } else {
                    JsonObject jsonContent = new JsonParser().parse(content.toString())
                                                            .getAsJsonObject();
                    JsonObject solarData = jsonContent.get("SolarData").getAsJsonObject();
                    String time = solarData.get("timestamp").getAsString();
                    time = time.replace("T", " ");
                    time = time.replace("Z",  "");
                    Timestamp currentTimestamp = Timestamp.valueOf(time);
                    newGeneratorGeneration = new GeneratorGeneration(
                        solarData.get("generation").getAsInt(),
                        generator,
                        currentTimestamp,
                        "ONLINE"
                    );
                    generation.put("generatorGenerationTimestamp", currentTimestamp.toString());
                    generation.put("generatorGeneration", newGeneratorGeneration.getGeneratorGeneration());
                    if (simpMessagingTemplate != null) {
                        simpMessagingTemplate.convertAndSend("/generator/" + generator.getGeneratorId() + "/generation", generation);
                    }
                    // System.out.println("Published generation of generator " + generator.getGeneratorId() + " at " + currentTimestamp);
                    generationValue += newGeneratorGeneration.getGeneratorGeneration();
                }
                generationRepository.save(newGeneratorGeneration);
            }
            generation.put("generatorGenerationTimestamp", currentTimestamp1.toString());
            generation.put("generatorGeneration", generationValue);
            if (simpMessagingTemplate != null) {
                simpMessagingTemplate.convertAndSend("/home/generation", generation);
            }
            // System.out.println("Published home generation at " + currentTimestamp1.toString());
        } catch (Exception e) {
            System.out.println("Couldn't get generation data!");
            System.out.println("Error:  " + e.getMessage() + " " + e.getCause());
        }
    }
}