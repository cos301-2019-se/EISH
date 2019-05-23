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
import com.monotoneid.eishms.exception.DevicesDoesNotExistException;
import com.monotoneid.eishms.device_manager.DeviceManager;
import com.monotoneid.eishms.exception.DeviceConsumptionDoesNotExistException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RequestMapping("api")
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class EndPointController{

    @Autowired
    private DevicesRepository devicesRepository;
    @Autowired
    private DeviceConsumptionRepository deviceconsumptionRepository;
    @Autowired
    private GeneratorGenerationRepository generatorgenerationRepository;
    @Autowired
    private GeneratorsRepository generatorsRepository;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private DeviceManager dm;

    public EndPointController() {
    }
    //Get Mapping
    @GetMapping("/view/devices")
    public ArrayNode getDevices() {
        List<Devices> allDevices = devicesRepository.findAll();
        ObjectNode objectNode = mapper.createObjectNode();
        ObjectNode insideObjects = mapper.createObjectNode();
        ArrayNode arrayObjects = mapper.createArrayNode();

        Devices curDevice;
        for (int i=0; i < allDevices.size(); i++) {
            curDevice = allDevices.get(i);
            insideObjects.put("device_id", curDevice.getDeviceId());
            insideObjects.put("device_name", curDevice.getDeviceName());
            insideObjects.put("device_type", curDevice.getDeviceType());
            insideObjects.put("device_state", curDevice.getDeviceState());
            arrayObjects.add(insideObjects);
            insideObjects = mapper.createObjectNode();
        }
       // objectNode.put("data", arrayObjects);
        //return objectNode;
        return arrayObjects;
    }
    @GetMapping("/view/generators")
    @CrossOrigin(origins = "http://localhost:4200")
    public ArrayNode getGenerators(){
        List<Generators> allGenerators = generatorsRepository.findAll();
        ObjectNode objectNode = mapper.createObjectNode();
        ObjectNode insideObjects = mapper.createObjectNode();
        ArrayNode arrayObjects = mapper.createArrayNode();

        Generators currentGenerator;
        for(int i=0;i<allGenerators.size();i++){
            currentGenerator=allGenerators.get(i);
            insideObjects.put("generator_name", currentGenerator.getGeneratorName());
            insideObjects.put("generator_type", currentGenerator.getGeneratorType());
            //insideObjects.put("generator_state", currentGenerator.getGeneratorState());
            arrayObjects.add(insideObjects);
            insideObjects = mapper.createObjectNode();

        }
        //objectNode.put("data", arrayObjects);
        //return objectNode;
        return arrayObjects;
    }
    /*
    @GetMapping("/view/device/consumption/{device_id}")
    public ObjectNode viewDeviceConsumptionFromToTime(@PathVariable(value = "device_id") Long device_id,@RequestBody DeviceRequestBody drb){
        ObjectNode objectNode = mapper.createObjectNode();
        ObjectNode insideObjects = mapper.createObjectNode();
        ArrayNode arrayObjects = mapper.createArrayNode();

         
        List<DeviceConsumption> alldeviceconsumption= deviceconsumptionRepository.findAllById()
                                                    .orElseThrow(() -> new DeviceConsumptionDoesNotExistException(device_id));
       
        

        DeviceConsumption currentDeviceConsumption;

        for(int i=0;i<alldeviceconsumption.size();i++){
            currentDeviceConsumption=alldeviceconsumption.get(i);
            insideObjects.put("date_time",currentDeviceConsumption.getTimeOfConsumption());
            insideObjects.put("consumption",currentDeviceConsumption.getConsumption());
            arrayObjects.add(insideObjects);
            insideObjects = mapper.createObjectNode();
        }
      
        objectNode.put("data",arrayObjects);
        return objectNode;
    }
  */

   
   @GetMapping("/")
   public ObjectNode start(){
    ObjectNode objectNode = mapper.createObjectNode();
    ArrayNode arrayObjects = mapper.createArrayNode();
    objectNode.put("testing get endpoint", arrayObjects);
    return objectNode;
   }

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
        
        dm.addDevice(newDevice);

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

   @PatchMapping("/control/device")
   @CrossOrigin(origins = "http://localhost:4200")
   public ObjectNode controlDevice(/*@PathVariable(value = "device_id") Long device_id*/ @RequestBody DeviceRequestBody drb){
    Devices currentDevice = devicesRepository.findById(drb.getDeviceID())
                                          .orElseThrow(() -> new DeviceConsumptionDoesNotExistException(drb.getDeviceID()));

    ObjectNode objectNode = mapper.createObjectNode();
    ObjectNode insideObjects = mapper.createObjectNode();
    Devices returnDevice;
    dm.toggle(currentDevice.getDeviceId());
    currentDevice.setDeviceState(!currentDevice.getDeviceState());
    insideObjects.put("device_id", currentDevice.getDeviceId());
    insideObjects.put("device_name", currentDevice.getDeviceName());
    insideObjects.put("device_state", currentDevice.getDeviceState());
     
    if((returnDevice = devicesRepository.save(currentDevice)) != null){
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