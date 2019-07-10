package com.monotoneid.eishms.integrationTests;

import com.monotoneid.eishms.dataPersistence.models.Device;
import com.monotoneid.eishms.dataPersistence.repositories.Devices;
import com.monotoneid.eishms.exceptions.ResourceNotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import static com.monotoneid.eishms.dataPersistence.models.DevicePriorityType.PRIORITY_NICETOHAVE;
import static org.assertj.core.api.Assertions.*;



@RunWith(SpringRunner.class)
@DataJpaTest
public class DevicesIntegrationTests{

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private Devices devicesRepository;
    
    @Test
    public void testRetrieveDeviceByIdGivenDeviceIsInRepository_ShouldRespondADeviceObjectFromRepo(){
        String[] newStates = {"ON","OFF","OFFLINE"};
        Device deviceToFind = new Device("LenovoLaptop","sonoff-Lenovolaptop","PRIORITY_NICETOHAVE",newStates);
        // deviceToFind.setDeviceName("LenovoLaptop");
        // deviceToFind.setDeviceTopic("sonoff-Lenovolaptop");
        // deviceToFind.setDevicePriorityType(PRIORITY_NICETOHAVE);
        // deviceToFind.setDeviceStates(newStates);
        entityManager.persist(deviceToFind);
        entityManager.flush();

        Device foundDevice = devicesRepository.findById(deviceToFind.getDeviceId())
                .orElseThrow(() -> new ResourceNotFoundException("Device does not exist"));
        
                assertThat(foundDevice.getDeviceName())
                    .isEqualTo(deviceToFind.getDeviceName());
    } 

}