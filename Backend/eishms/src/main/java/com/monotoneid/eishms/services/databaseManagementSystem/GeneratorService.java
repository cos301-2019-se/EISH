package com.monotoneid.eishms.services.databaseManagementSystem;

import static com.monotoneid.eishms.datapersistence.models.GeneratorPriorityType.PRIORITY_ALWAYSUSE;
import static com.monotoneid.eishms.datapersistence.models.GeneratorPriorityType.PRIORITY_NEUTRAL;
import static com.monotoneid.eishms.datapersistence.models.GeneratorPriorityType.PRIORITY_USEWHENCRITICAL;
import static com.monotoneid.eishms.datapersistence.models.GeneratorPriorityType.PRIORITY_USEWHENEMPTY;

import java.util.List;

import com.monotoneid.eishms.datapersistence.models.Generator;
import com.monotoneid.eishms.datapersistence.repositories.Generators;
import com.monotoneid.eishms.exceptions.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import net.minidev.json.JSONObject;

@Service
public class GeneratorService {

    @Autowired
    private Generators generatorsRepository;
     /**
     * Retrieves all the generators in the database
     * @return List of all generators
     */
    public List<Generator> retrieveAllGenerators(){
        return generatorsRepository.findAll();
    }

    /**
     * Retrieves the generator with the specified id
     * @param generatorId
     * @return the generator
     * @exception ResourceNotFound
     */
    public ResponseEntity<Generator> retrieveGenerator(long generatorId){
        try {
            Generator foundGenerator = generatorsRepository.findById(generatorId)
                    .orElseThrow(() -> new ResourceNotFoundException("Generator does not exist"));
            return new ResponseEntity<>(foundGenerator, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + "!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Adds a new generator into the database with the specified data
     * @param newGenerator
     * @return Object message
     * @exception null
     */
    public ResponseEntity<Object> addGenerator(Generator newGenerator) {
        try {
            if (newGenerator == null) {
                throw null;
            }
            if (newGenerator.getGeneratorName().isEmpty() 
                    || newGenerator.getGeneratorUrl().isEmpty()
                    || newGenerator.getGeneratorPriority() == null
                    || newGenerator.getGeneratorStates()==null) {
                throw null;
            }
            if (newGenerator.getGeneratorPriority() != PRIORITY_ALWAYSUSE && newGenerator.getGeneratorPriority() != PRIORITY_USEWHENCRITICAL
                    && newGenerator.getGeneratorPriority() != PRIORITY_NEUTRAL
                    && newGenerator.getGeneratorPriority() != PRIORITY_USEWHENEMPTY) {
                    throw null;
                }
                if (newGenerator.getGeneratorName() != null && newGenerator.getGeneratorUrl() != null
                && newGenerator.getGeneratorPriority() != null && newGenerator.getGeneratorStates()!=null) {
           
                    generatorsRepository.save(newGenerator);
                   
                    JSONObject responseObject = new JSONObject();
                    responseObject.put("message","Generator added!");
                    return new ResponseEntity<>(responseObject,HttpStatus.OK);
                } else
                    throw null;    
        } catch(Exception e) {
            System.out.println("Error: Input is " + e.getMessage() + "!");
            return new ResponseEntity<>("Error: Failed to add generator details!",HttpStatus.PRECONDITION_FAILED);
        }
    }
    
    /**
     * Updates the data of the specified with parsed in data
     * @param newGenerator
     * @return Object message
     * @exception null
     * @exception ResourceNotFound
     */
    public ResponseEntity<Object> updateGenerator(Generator newGenerator) {
        try {
            if (newGenerator == null) {
                throw null;
            }
            if (newGenerator.getGeneratorName().isEmpty() 
                    || newGenerator.getGeneratorUrl().isEmpty()
                    || newGenerator.getGeneratorPriority() == null
                    || newGenerator.getGeneratorStates()==null) {
                throw null;
            }
            if (newGenerator.getGeneratorPriority() != PRIORITY_ALWAYSUSE && newGenerator.getGeneratorPriority() != PRIORITY_USEWHENCRITICAL
                    && newGenerator.getGeneratorPriority() != PRIORITY_NEUTRAL
                    && newGenerator.getGeneratorPriority() != PRIORITY_USEWHENEMPTY) {
                    throw null;
                }
                if (newGenerator.getGeneratorName() != null && newGenerator.getGeneratorUrl() != null
                && newGenerator.getGeneratorPriority() != null && newGenerator.getGeneratorStates()!=null) {
                    Generator foundGenerator = generatorsRepository.findById(newGenerator.getGeneratorId())
                            .orElseThrow(() -> new ResourceNotFoundException("Generator does not exist!"));
                            foundGenerator.setGeneratorName(newGenerator.getGeneratorName());
                            foundGenerator.setGeneratorPriorityType(newGenerator.getGeneratorPriority());
                            foundGenerator.setGeneratorUrl(newGenerator.getGeneratorUrl());
                            foundGenerator.setGeneratorStates(newGenerator.getGeneratorStates());
                    generatorsRepository.save(foundGenerator);
                    JSONObject responseObject = new JSONObject();
                    responseObject.put("message","Generator Updated!");
                    return new ResponseEntity<>(responseObject,HttpStatus.OK);
            }else{
                throw null;
            }    
        } catch(Exception e) {
            System.out.println("Error: Input is " + e.getMessage() + "!");
            if(e.getCause() == null)
                return new ResponseEntity<>("Error: Failed to update generator!",HttpStatus.PRECONDITION_FAILED);
            else
                return new ResponseEntity<>("Error: Failed to update generator!",HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Removes the specified generator if it exists
     * @param generator
     * @return Object message
     * @exception null
     * @exception ResourceNotFound
     */
    public ResponseEntity<Object> removeGenerator(long generatorId) {
        try {
            generatorsRepository.findById(generatorId).orElseThrow(() -> new ResourceNotFoundException("Generator does not exist"));
            generatorsRepository.deleteById(generatorId);
            JSONObject responseObject = new JSONObject();
            responseObject.put("message", "Success: Generator has been deleted!");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error: Input is " + e.getMessage() + "!");
            return new ResponseEntity<>("Error: Failed to delete Generator!", HttpStatus.NOT_FOUND);
        }
    }       

    
}