package com.monotoneid.eishms.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/device");
        config.enableSimpleBroker("/home");
        config.enableSimpleBroker("/weather");
        config.enableSimpleBroker("/battery");
        //config.enableSimpleBroker("/generation");
        config.enableSimpleBroker("/generator");
        config.enableSimpleBroker("/notification");
        config.enableSimpleBroker("");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/deviceStatus").setAllowedOrigins("*");
        registry.addEndpoint("/deviceStatus").setAllowedOrigins("*").withSockJS();
    }

}