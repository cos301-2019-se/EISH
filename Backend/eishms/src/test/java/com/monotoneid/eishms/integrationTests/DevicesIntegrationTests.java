package com.monotoneid.eishms.integrationTests;

// import com.monotoneid.eishms.dataPersistence.models.Device;
// import com.monotoneid.eishms.dataPersistence.repositories.Devices;
// import com.monotoneid.eishms.exceptions.ResourceNotFoundException;

// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
// import org.springframework.test.context.ActiveProfiles;
// import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
// import org.springframework.test.context.junit4.SpringRunner;
// import static com.monotoneid.eishms.dataPersistence.models.DevicePriorityType.PRIORITY_NICETOHAVE;
// import static org.assertj.core.api.Assertions.*;
// import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

// import java.util.List;


//@ActiveProfiles("test")
//@RunWith(SpringRunner.class)
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = NONE)
public class DevicesIntegrationTests{

    // @Autowired
    // private TestEntityManager entityManager;

    // @Autowired
    // private Devices devicesRepository;



    // @Test
    // public void testRetrieveAllDevicesInRepositoryGivenOneDevice_ShouldRespondWithAListWithOneDevice(){
    //     String[] newStates = {"ON","OFF","OFFLINE"};
    //     Device deviceToFind = new Device();
    //     deviceToFind.setDeviceName("LenovoLaptop");
    //     deviceToFind.setDeviceTopic("sonoff-Lenovolaptop");
    //     deviceToFind.setDevicePriorityType(PRIORITY_NICETOHAVE);
    //     deviceToFind.setDeviceStates(newStates);
    //     entityManager.persist(deviceToFind);
    //     entityManager.flush();

    //     List<Device> foundDevices =devicesRepository.findAll();
    //     System.out.println(foundDevices.toString());
    //     assertThat(foundDevices.size()).isEqualTo(1);

    // }
    
    // @Test
    // public void testRetrieveDeviceByIdGivenDeviceIsInRepository_ShouldRespondADeviceObjectFromRepo(){
    //     String[] newStates = {"ON","OFF","OFFLINE"};
    //     Device deviceToFind = new Device();
    //     deviceToFind.setDeviceName("LenovoLaptop");
    //     deviceToFind.setDeviceTopic("sonoff-Lenovolaptop");
    //     deviceToFind.setDevicePriorityType(PRIORITY_NICETOHAVE);
    //     deviceToFind.setDeviceStates(newStates);
    //     entityManager.persist(deviceToFind);
    //     entityManager.flush();

    //     Device foundDevice = devicesRepository.findById(deviceToFind.getDeviceId())
    //             .orElseThrow(() -> new ResourceNotFoundException("Device does not exist"));
        
    //             assertThat(foundDevice.getDeviceName())
    //                 .isEqualTo(deviceToFind.getDeviceName());
    // } 
    
}