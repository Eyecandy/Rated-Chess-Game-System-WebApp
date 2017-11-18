package io.muic.designpattern.controllers;

import io.muic.designpattern.model.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {
    @MessageMapping("/msg")
    @SendTo("/sub/message")
    public Message message(Message message) throws Exception {
        return new io.muic.designpattern.model.Message(message.getFrom(), message.getMessage());
    }
}
