package io.muic.designpattern.controllers;

import io.muic.designpattern.model.Message;
import io.muic.designpattern.model.Reply;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {
    @MessageMapping("/msg")
    @SendTo("/sub/message")
    public Reply reply(Message message) throws Exception {
        return new Reply("Hello " + message.getFrom() + "!, You are the best!");
    }
}
