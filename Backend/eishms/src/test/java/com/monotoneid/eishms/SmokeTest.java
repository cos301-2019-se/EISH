package com.monotoneid.eishms;
import static org.assertj.core.api.Assertions.assertThat;

import com.monotoneid.eishms.communications.controller.BatteryCapacityEndPointController;
import com.monotoneid.eishms.communications.controller.DeviceConsumptionEndPointController;
import com.monotoneid.eishms.communications.controller.DeviceEndPointController;
import com.monotoneid.eishms.communications.controller.GeneratorEndPointController;
import com.monotoneid.eishms.communications.controller.GeneratorGenerationEndPointController;
import com.monotoneid.eishms.communications.controller.HomeConsumptionEndPointController;
import com.monotoneid.eishms.communications.controller.HomeGenerationEndPointController;
import com.monotoneid.eishms.communications.controller.JwtAuthenticationController;
import com.monotoneid.eishms.communications.controller.NotificationEndPointController;
import com.monotoneid.eishms.communications.controller.SocketController;
import com.monotoneid.eishms.communications.controller.UserEndPointController;
import com.monotoneid.eishms.communications.controller.UserPresenceEndPointController;
import com.monotoneid.eishms.communications.controller.WeatherEndPointController;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmokeTest {

    @Autowired
    private DeviceEndPointController deviceController;

    @Autowired
    private BatteryCapacityEndPointController batteryController;

    @Autowired
    private DeviceConsumptionEndPointController deviceConsumptionController;

    @Autowired
    private GeneratorGenerationEndPointController generatorGenerationController;

    @Autowired 
    private GeneratorEndPointController generatorController;

    @Autowired
    private HomeConsumptionEndPointController homeConsumptionController;

    @Autowired
    private HomeGenerationEndPointController homeGenerationController;

    @Autowired
    private JwtAuthenticationController JwtController;

    @Autowired
    private NotificationEndPointController notificationController;

    @Autowired
    private UserEndPointController userController;

    @Autowired
    private UserPresenceEndPointController userPresenceController;

    @Autowired
    private WeatherEndPointController weatherController;

    @Autowired
    private SocketController socketController;


    @Test
    public void contexLoadsDeviceEndPointController() throws Exception {
        assertThat(deviceController).isNotNull();
    }

    @Test
    public void contexLoadsBatteryCapacityEndPointController() throws Exception {
        assertThat(batteryController).isNotNull();
    }

    @Test
    public void contexLoadsDeviceConsumptionEndPointController() throws Exception {
        assertThat(deviceConsumptionController).isNotNull();
    }

    @Test
    public void contexLoadsGeneratorGenerationEndPointController() throws Exception {
        assertThat(generatorGenerationController).isNotNull();
    }

    @Test
    public void contexLoadsHomeConsumptionEndPointController() throws Exception {
        assertThat(homeConsumptionController).isNotNull();
    }

    @Test
    public void contexLoadsGeneratorEndPointController() throws Exception {
        assertThat(generatorController).isNotNull();
    }

    

    @Test
    public void contexLoadsHomeGenerationEndPointController() throws Exception {
        assertThat(homeGenerationController).isNotNull();
    }

    @Test
    public void contexLoadsJwtAuthenticationController() throws Exception {
        assertThat(JwtController).isNotNull();
    }

    @Test
    public void contexLoadsNotificationEndPointController() throws Exception {
        assertThat(notificationController).isNotNull();
    }

    @Test
    public void contexLoadsUserEndPointController() throws Exception {
        assertThat(userController).isNotNull();
    }

    @Test
    public void contexLoadsUserPresenceEndPointController() throws Exception {
        assertThat(userPresenceController).isNotNull();
    }

    @Test
    public void contexLoadsWeatherEndPointController() throws Exception {
        assertThat(weatherController).isNotNull();
    }

    @Test
    public void contexLoadsSocketController() throws Exception {
        assertThat(socketController).isNotNull();
    }
}