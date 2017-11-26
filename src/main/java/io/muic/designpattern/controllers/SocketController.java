package io.muic.designpattern.controllers;

import io.muic.designpattern.model.MyMessage;
import io.muic.designpattern.model.Reply;
import io.muic.designpattern.services.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
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

//    @Autowired
//    public SocketController(SimpMessagingTemplate template){
//        this.template = template;
//    }

    @MessageMapping("/msg")
    @SendTo("/sub/message")
    public Reply reply(MyMessage message,SimpMessageHeaderAccessor simpMessageHeaderAccessor) throws Exception {
        String s = subscriberService.getSubscribers().get(simpMessageHeaderAccessor.getSessionId());
        System.out.println(s + " HEY FROM ME");
        if (message.getFrom().equals("get em")) {
            System.out.println(subscriberService.getSubscribers().values());
        }
        return new Reply(message.getFrom() + " YEYEY");
    }

    @MessageMapping(value = "/msg/{id}")
    @SendTo("/sub/game")
    public Reply reply1(SimpMessageHeaderAccessor simpMessageHeaderAccessor, MyMessage message, @DestinationVariable long id) throws Exception {
        System.out.println("Chess game player : " + message.getFrom());
        System.out.println(message.getFrom() + " TEST " + id);
        String s = subscriberService.getSubscribers().get(simpMessageHeaderAccessor.getSessionId());
        return new Reply("In Game " + s);
//        ChessGame chessGame= ChessGameService.findOne(id);
//        .....
    }
}