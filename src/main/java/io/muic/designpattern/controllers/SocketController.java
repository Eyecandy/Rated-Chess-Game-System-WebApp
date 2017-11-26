package io.muic.designpattern.controllers;

import io.muic.designpattern.model.MyMessage;
import io.muic.designpattern.model.Reply;
import io.muic.designpattern.services.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class SocketController {

    @Autowired
    SubscriberService subscriberService;
    @MessageMapping("/msg")
    @SendTo("/sub/message")
    @SubscribeMapping("sub/message/}")
    public Reply reply(MyMessage message,SimpMessageHeaderAccessor simpMessageHeaderAccessor) throws Exception {
        String s = subscriberService.getSubscribers().get(simpMessageHeaderAccessor.getSessionId());
        System.out.println(s + " HEY FROM ME");
        if (message.getFrom().equals("get em")) {
            System.out.println(subscriberService.getSubscribers().values());
        }
        return new Reply(message.getFrom() + " YEYEY");
    }
}