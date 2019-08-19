package com.monotoneid.eishms.communications.controller;

import com.monotoneid.eishms.datapersistence.models.Generator;
import com.monotoneid.eishms.services.databaseManagementSystem.GeneratorService;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *CLASS GENERATOR ENDPOINT CONTROLLER. 
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class GeneratorEndPointController {

    @Autowired
    private GeneratorService generatorService;

   
    /**
    * POST METHOD.
    * Implements the Generator endpoint, that calls the addGenerator service
    * @param newGenerator represents the new generator to be added
    * @return the status message
    */
    @PostMapping("/generator")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> addGenerator(@Valid @RequestBody Generator newGenerator) {
        return generatorService.addGenerator(newGenerator);
    }
 
    /**
     * PUT METHOD.
     * Implements updateGenerator endpoint, that calls the updateGenerator service
     * @param newGenerator represents the new generator to be update
     * @return object message
     */
    @PutMapping("/generator")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> updateGenerator(@Valid @RequestBody Generator newGenerator) {
        return generatorService.updateGenerator(newGenerator);
    }
     
    /**
     * GET METHOD.
     * Implements retrieveAllGenerators endpoint, that calls the retrieveAllGenerators service
     * @return an object with all generators 
     */
    @GetMapping("/generators")
    @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
    public List<Generator> retrieveAllGenerators() {
        return generatorService.retrieveAllGenerators();
    }
 
    /**
     * GET METHOD.
     * Implements retrieveGenerator endpoint, that calls the retrieveGenerator service
     * @param generatorId represents the generators id
     * @return a the valid Generator
     */
    @GetMapping(value = "/generator",params = {"generatorId"})
    //@PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
    public ResponseEntity<Generator> retriveGenerator(
        @Valid @RequestParam(value = "generatorId") long generatorId) {
        return generatorService.retrieveGenerator(generatorId);
    }
   
    /**
     * DELETE METHOD.
     * Implements removeGenerator endpoint, that calls the removeGenerator service
     * @param generatorId represnts the generator id
     * @return an object with deleted generator
     */
    @DeleteMapping("/generator/{generatorId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> removeGenerator(@PathVariable long generatorId) {
        return generatorService.removeGenerator(generatorId);
    }
}