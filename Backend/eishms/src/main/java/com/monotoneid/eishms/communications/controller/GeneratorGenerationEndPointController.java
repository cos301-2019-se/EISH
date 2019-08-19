package com.monotoneid.eishms.communications.controller;

import com.monotoneid.eishms.datapersistence.models.GeneratorGeneration;
import com.monotoneid.eishms.services.databaseManagementSystem.GeneratorGenerationService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *CLASS GENERATOR GENERATION ENDPOINT CONTROLLER. 
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/generator")
public class GeneratorGenerationEndPointController {

    @Autowired
    private GeneratorGenerationService generatorGenerationService;

    /**
    * GET METHOD.
    * Implements retrieveGeneratorGeneration endpoint, 
        that calls the retrieveGeneratorGeneration service
    * @return a the valid GeneratorGenerationList
    */
     
    @GetMapping(value = "/generation", params = {"generatorId","startTimeStamp","endTimeStamp"})
    @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
    public List<GeneratorGeneration> retrieveGeneratorGenerationCases(
        @RequestParam(value = "generatorId", required = true) long generatorId,
        @RequestParam(value = "startTimeStamp", required = true) String startTimeStamp,
        @RequestParam(value = "endTimeStamp", required = true) String endTimeStamp) {
        return generatorGenerationService
                .retrieveAllGeneratorCases(generatorId, startTimeStamp, endTimeStamp);
    }

    @GetMapping(value = "/generation", params = {"generatorId","interval"})
    @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
    public List<GeneratorGeneration> retrieveGeneratorGenerationBetweenInterval(
        @RequestParam(value = "generatorId", required = true) long generatorId,
        @RequestParam(value = "interval", required = true) String interval) {
        return generatorGenerationService.retrieveBetweenInterval(generatorId, interval);
    }

}