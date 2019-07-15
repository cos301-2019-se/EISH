package com.monotoneid.eishms.services.databaseManagementSystem;

import java.sql.Timestamp;
import java.util.List;

import com.monotoneid.eishms.dataPersistence.models.Generator;
import com.monotoneid.eishms.dataPersistence.models.GeneratorGeneration;
import com.monotoneid.eishms.dataPersistence.repositories.GeneratorGenerations;
import com.monotoneid.eishms.dataPersistence.repositories.Generators;
import com.monotoneid.eishms.exceptions.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GeneratorGenerationService{
   
    @Autowired
    private GeneratorGenerations generatorGenartionRepository;

    @Autowired
    private Generators generatorsRepository; 

    public void addGeneratorGenration(long referenceDeviceId,  Timestamp newGeneratorGenrationTimestamp, String newGeneratorGenrationState, float newGeneratorGenration){
        try{            
            Generator foundGenerator = generatorsRepository.findById(referenceDeviceId)
                    .orElseThrow(() -> new ResourceNotFoundException("Generator does not exist"));
            GeneratorGeneration newGeneratorGenarationToAdd = new GeneratorGeneration(newGeneratorGenration, foundGenerator, newGeneratorGenrationTimestamp, newGeneratorGenrationState);
            generatorGenartionRepository.save(newGeneratorGenarationToAdd);        
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + "!");
        }
   }

    
   public List<GeneratorGeneration> retrieveAllGeneratorCases(long generatorId, String startTimeStamp, String endTimeStamp){
        try{
            generatorsRepository.findById(generatorId).orElseThrow(() -> new ResourceNotFoundException("generator does not exist!"));
            
            String removeQuotesStartTimeStamp =startTimeStamp.replaceAll("^\"|\"$","");
            Timestamp convertedStartTimestamp = Timestamp.valueOf(removeQuotesStartTimeStamp);

            String removeQuotesEndTimeStamp =endTimeStamp.replaceAll("^\"|\"$","");
            Timestamp convertedEndTimestamp = Timestamp.valueOf(removeQuotesEndTimeStamp);

            List<GeneratorGeneration> foundGeneratorGenerationList = 
            generatorGenartionRepository.findByGeneratorGenerationIdAndGeneratorGenerationTimestampBetween(generatorId,convertedStartTimestamp,convertedEndTimestamp)
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundGeneratorGenerationList;
        } catch(Exception e) {
            System.out.println("Error: " + e.getMessage() + "!");
            throw e;
        }
    }  
    
    public List<GeneratorGeneration> retrieveBetweenInterval(long generatorId,String interval){
        try{
            
            generatorsRepository.findById(generatorId).orElseThrow(() -> new ResourceNotFoundException("generator does not exist!"));
              if(interval.matches("last10minutes")){
                List<GeneratorGeneration> foundGeneratorGenerationList =  generatorGenartionRepository.findGeneratorGenerationLastTenMinutes(generatorId)
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundGeneratorGenerationList;

              }else if(interval.matches("lasthour")){
                List<GeneratorGeneration> foundGeneratorGenerationList =  generatorGenartionRepository.findGeneratorGenerationLastHour(generatorId)
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundGeneratorGenerationList;

              }else if(interval.matches("lastday")){
                List<GeneratorGeneration> foundGeneratorGenerationList =  generatorGenartionRepository.findGeneratorGenerationLastOneDay(generatorId)
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundGeneratorGenerationList;

              }else if(interval.matches("lastweek")){
                List<GeneratorGeneration> foundGeneratorGenerationList =  generatorGenartionRepository.findGeneratorGenerationLastOneWeek(generatorId)
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundGeneratorGenerationList;
              }else if(interval.matches("lastmonth")){
                List<GeneratorGeneration> foundGeneratorGenerationList =  generatorGenartionRepository.findGeneratorGenerationLastOneMonth(generatorId)
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundGeneratorGenerationList;
              }else if(interval.matches("lastyear")){
                List<GeneratorGeneration> foundGeneratorGenerationList =  generatorGenartionRepository.findGeneratorGenerationLastOneYear(generatorId)
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundGeneratorGenerationList;
              }else if(interval.matches("thishour")){
                List<GeneratorGeneration> foundGeneratorGenerationList =  generatorGenartionRepository.findByGeneratorGenerationIdForThisHour(generatorId)
                .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundGeneratorGenerationList;
              }else if(interval.matches("thisday")){
                List<GeneratorGeneration> foundGeneratorGenerationList =  generatorGenartionRepository.findByGeneratorGenerationIdForThisDay(generatorId)
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundGeneratorGenerationList;
              }else if(interval.matches("thisweek")){
                List<GeneratorGeneration> foundGeneratorGenerationList =  generatorGenartionRepository.findByGeneratorGenerationIdForThisWeek(generatorId)
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundGeneratorGenerationList;
              }else if(interval.matches("thismonth")){
                List<GeneratorGeneration> foundGeneratorGenerationList =  generatorGenartionRepository.findByGeneratorGenerationIdForThisMonth(generatorId)
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundGeneratorGenerationList;
              } else if(interval.matches("thisyear")){
                List<GeneratorGeneration> foundGeneratorGenerationList =  generatorGenartionRepository.findByGeneratorGenerationIdForThisYear(generatorId)
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundGeneratorGenerationList;
              }else{
                  throw null;
              }
        } catch(Exception e) {
            System.out.println("Error: " + e.getMessage() + "!");
            throw e;
        }
    }

}
 