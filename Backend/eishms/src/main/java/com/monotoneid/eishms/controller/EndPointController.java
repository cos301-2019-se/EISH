package com.monotoneid.eishms.controller;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.monotoneid.eishms.repository.DevicesRepository;
import com.monotoneid.eishms.model.Devices;
import com.monotoneid.eishms.model.Generators;
import com.monotoneid.eishms.model.DeviceConsumption;
import com.monotoneid.eishms.model.DeviceRequestBody;
import com.monotoneid.eishms.repository.DeviceConsumptionRepository;
import com.monotoneid.eishms.repository.GeneratorGenerationRepository;
import com.monotoneid.eishms.repository.GeneratorsRepository;
import com.monotoneid.eishms.services.DeviceConsumptionService;
import com.monotoneid.eishms.exception.DevicesDoesNotExistException;
import com.monotoneid.eishms.exception.DeviceConsumptionDoesNotExistException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api")
@RestController
public class EndPointController {

    @Autowired
    private DevicesRepository devicesRepository;
    @Autowired
    private DeviceConsumptionRepository deviceconsumptionRepository;
    @Autowired
    private GeneratorGenerationRepository generatorgenerationRepository;
    @Autowired
    private GeneratorsRepository generatorsRepository;
   // @Autowired
   // private DeviceConsumptionService deviceconsumptionservice;

    private long start_date=0, end_date=0; 

    @Autowired
    ObjectMapper mapper;

    // Get Mapping
    @GetMapping("/view/devices")
    @CrossOrigin(origins = "http://localhost:4200")
    public ArrayNode getDevices() {
        List<Devices> allDevices = devicesRepository.findAll();
        ObjectNode objectNode = mapper.createObjectNode();
        ObjectNode insideObjects = mapper.createObjectNode();
        ArrayNode arrayObjects = mapper.createArrayNode();

        Devices curDevice;
        for (int i = 0; i < allDevices.size(); i++) {
            curDevice = allDevices.get(i);
            insideObjects.put("device_id", curDevice.getDeviceId());
            insideObjects.put("device_name", curDevice.getDeviceName());
            insideObjects.put("device_type", curDevice.getDeviceType());
            insideObjects.put("device_state", curDevice.getDeviceState());
            arrayObjects.add(insideObjects);
            insideObjects = mapper.createObjectNode();
        }
       // objectNode.put("data", arrayObjects);
        return arrayObjects;
    }

    @GetMapping("/view/generators")
    @CrossOrigin(origins = "http://localhost:4200")
    public ArrayNode getGenerators() {
        List<Generators> allGenerators = generatorsRepository.findAll();
        ObjectNode objectNode = mapper.createObjectNode();
        ObjectNode insideObjects = mapper.createObjectNode();
        ArrayNode arrayObjects = mapper.createArrayNode();

        Generators currentGenerator;
        for (int i = 0; i < allGenerators.size(); i++) {
            currentGenerator = allGenerators.get(i);
            insideObjects.put("generator_name", currentGenerator.getGeneratorName());
            insideObjects.put("generator_type", currentGenerator.getGeneratorType());
            // insideObjects.put("generator_state", currentGenerator.getGeneratorState());
            arrayObjects.add(insideObjects);
            insideObjects = mapper.createObjectNode();

        }
        //objectNode.put("data", arrayObjects);
        return arrayObjects;
    }

    @GetMapping(value="/view/device/consumption/{device_id}",params={"start_date","end_date"})
    @CrossOrigin(origins = "http://localhost:4200")
    public ArrayNode viewDeviceConsumptionFromToTime(@PathVariable(value = "device_id") Long device_id,@RequestParam(value="start_date") long start_dateInput,@RequestParam(value="end_date") long end_dateInput) {
        start_date=start_dateInput;
        end_date=end_dateInput;
        ObjectNode objectNode = mapper.createObjectNode();
        ObjectNode insideObjects = mapper.createObjectNode();
        ArrayNode arrayObjects = mapper.createArrayNode();
        DeviceConsumption currentDeviceConsumption;
        Devices currentDevice=devicesRepository.findById(device_id)
                                          .orElseThrow(() -> new DeviceConsumptionDoesNotExistException(device_id));
        //currentDevice.get
        List<DeviceConsumption> alldeviceconsumption= currentDevice.getDeviceConsumption();
        //getDeviceConsumption();
                System.out.println("size of list " + alldeviceconsumption.size());

      // List<DeviceConsumption> alldeviceconsumption = deviceconsumptionRepository.getAllDeviceConsumptionByUsingId(device_id);
        
        for(int i=0;i<alldeviceconsumption.size();i++){
            currentDeviceConsumption=alldeviceconsumption.get(i);
            if(start_date>=0 && end_date>=0){
            if(currentDeviceConsumption.getTimeOfConsumption()<= end_date && currentDeviceConsumption.getTimeOfConsumption()>= start_date ){
            insideObjects.put("date_time",currentDeviceConsumption.getTimeOfConsumption());
            insideObjects.put("consumption",currentDeviceConsumption.getConsumption());
            arrayObjects.add(insideObjects);
            insideObjects = mapper.createObjectNode();
            }
        }
        }
      

        //objectNode.put("data",arrayObjects);
        return arrayObjects;
    }
  

   /*
   @GetMapping("/enterconsumption")
   public ObjectNode start(){
    ObjectNode objectNode = mapper.createObjectNode();
    ArrayNode arrayObjects = mapper.createArrayNode();
    
    
        DeviceConsumptionService.insertDeviceConsumption(1, (float) 102.8, 1532148465);
    objectNode.put("testing get endpoint", arrayObjects);
    return objectNode;
   }
   */
   //Post Mapping
   @PostMapping("/add/device")
   @CrossOrigin(origins = "http://localhost:4200")
   public ObjectNode addDevice(@RequestBody DeviceRequestBody drb){
        ObjectNode objectNode = mapper.createObjectNode();
        Devices newDevice = new Devices();
        Devices returnDevice;
        newDevice.setDeviceName(drb.getDeviceName());
        newDevice.setDeviceTopic(drb.getDeviceTopic());
        newDevice.setDeviceType(drb.getDeviceType());
        newDevice.setDeviceMaxWatt(drb.getMaxWatt());
        newDevice.setDeviceMinWatt(drb.getMinWatt());
        newDevice.setDeviceAutoStart(drb.getDeviceAutoStart());
        newDevice.setDeviceState(drb.getDeviceState());
        newDevice.setDevicePriority(drb.getDevicePriority());

        if ((returnDevice = devicesRepository.save(newDevice)) != null) {
            objectNode.put("data", returnDevice.getDeviceName() + " successfully inserted.");
        }else{
              objectNode.put("data", "Failed to insert " + newDevice.getDeviceName() + ".");
        }

        return objectNode;
   }
   @PostMapping("/add/generator")
   @CrossOrigin(origins = "http://localhost:4200")
   public ObjectNode addGenerator(@RequestBody DeviceRequestBody drb){
    ObjectNode objectNode = mapper.createObjectNode();
    Generators newGenerator = new Generators();
    Generators returnGenerator;

    newGenerator.setGeneratorName(drb.getGeneratorName());
    newGenerator.setGeneratorMinCapacity(drb.getGeneratorMinCapacity());
    newGenerator.setGeneratorMaxCapacity(drb.getGeneratorMaxCapacity());
    newGenerator.setGeneratorType(drb.getGeneratorType());
    newGenerator.setGeneratorTopic(drb.getGeneratorTopic());

    if ((returnGenerator = generatorsRepository.save(newGenerator)) != null) {
        objectNode.put("data", returnGenerator.getGeneratorName() + " successfully inserted.");
    }else{
          objectNode.put("data", "Failed to insert " + newGenerator.getGeneratorName() + ".");
    }
    return objectNode;
   }
   @PatchMapping("/control/device/{device_id}")
   @CrossOrigin(origins = "http://localhost:4200")
   public ObjectNode controlDevice(@PathVariable(value = "device_id") Long device_id,@RequestBody DeviceRequestBody drb){
    Devices currentDevice=devicesRepository.findById(device_id)
                                          .orElseThrow(() -> new DeviceConsumptionDoesNotExistException(device_id));

    boolean newDeviceState =drb.getDeviceState();
    ObjectNode objectNode = mapper.createObjectNode();
    ObjectNode insideObjects = mapper.createObjectNode();
    Devices returnDevice;
    if(currentDevice.getDeviceState()==false && newDeviceState==true){
        currentDevice.setDeviceState(true);
    }
    else if(currentDevice.getDeviceState()==true && newDeviceState==false){
        currentDevice.setDeviceState(false);
        //deviceManager.toggle();
    }
    else{
        objectNode.put("data", "Object already in " + currentDevice.getDeviceState() + " state.");
        return objectNode; 
    }
    insideObjects.put("device_name", currentDevice.getDeviceName());
    insideObjects.put("device_state", currentDevice.getDeviceState());
     
    if((returnDevice=devicesRepository.save(currentDevice))!=null){
        objectNode.put("data",insideObjects);
    }else{
        objectNode.put("data", "Failed to update " + currentDevice.getDeviceName() + ".");
    }
     
    return objectNode; 
   }










   /*
     //@GetMapping("/view/device/consumption/{device_name}")
      
     
    
   */
    

/*
    
    @GetMapping("/view/home/consumption")
    @GetMapping("/view/home/generation")
    @GetMapping("/view/generator/generation/{generator_name}")
    
*/
}