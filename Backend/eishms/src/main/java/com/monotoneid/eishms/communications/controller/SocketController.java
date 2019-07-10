package com.monotoneid.eishms.communications.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;

@Controller
public class SocketController {

	@Autowired
	private SimpMessageSendingOperations messagingTemplate;

	@MessageMapping("/message")
    @SendTo("/topic/reply")
	public String processMessageFromClient(@Payload String message) throws Exception {
		String name = new Gson().fromJson(message, Map.class).get("name").toString();
		return name;
	}
	
	@MessageExceptionHandler
    public String handleException(Throwable exception) {
        messagingTemplate.convertAndSend("/errors", exception.getMessage());
	    return exception.getMessage();
    }

}
