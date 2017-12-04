package io.muic.designpattern.controllers;

import io.muic.designpattern.components.*;
import io.muic.designpattern.model.*;
import io.muic.designpattern.services.ChessService;
import io.muic.designpattern.services.OnlineUserService;
import io.muic.designpattern.services.SubscriberService;
import io.muic.designpattern.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

@Controller
public class SocketController {

    @Autowired
    private SubscriberService subscriberService;

    @Autowired
    private ChessService chessService;

    @Autowired
    private UserService userService;

    @Autowired
    private OnlineUserService onlineUserService;

    private SimpMessagingTemplate template;

    private Map<String, ChessReplyStrategy> replyStrategies = new HashMap<>();

    @Autowired
    public SocketController(SimpMessagingTemplate template, SubscriberService subscriberService, ChessService chessService, UserService userService, OnlineUserService onlineUserService){
        this.template = template;
        this.subscriberService = subscriberService;
        replyStrategies.put("start", new StartStrategy(chessService, userService));
        replyStrategies.put("resume", new ResumeStrategy(onlineUserService, chessService));
        replyStrategies.put("move", new MoveStrategy(chessService, subscriberService));
    }

    @MessageMapping("/msg")
    public void reply(MyMessage message,SimpMessageHeaderAccessor headerAccessor) throws Exception {
        SimpMessageHeaderAccessor ha = SimpMessageHeaderAccessor
                .create(SimpMessageType.MESSAGE);
        ha.setSessionId(headerAccessor.getSessionId());
        ha.setLeaveMutable(true);
        String s = subscriberService.getSubscribers().get(headerAccessor.getSessionId());
        System.out.println(s + " HEY FROM ME");
        if (message.getFrom().equals("get em")) {
            System.out.println(subscriberService.getSubscribers().values());
        }
        template.convertAndSendToUser(headerAccessor.getSessionId()
                , "/sub/message", new Reply("This is a response!")
                , ha.getMessageHeaders());
    }

    @MessageMapping("/create_game")
    public void create_game(MyMessage message,SimpMessageHeaderAccessor headerAccessor) throws Exception {
        System.out.println(subscriberService.getSubscribers().values());
        String host = subscriberService.getSubscribers().get(headerAccessor.getSessionId());
        Chess chess = new Chess();
        chess.setHost(userService.findUserByUsername(host));
        chess.setCurrentPlayer(1);
        chess.setComplete(false);
        chessService.saveChess(chess);
        GameRequest response = new GameRequest(chess.getId());
        SimpMessageHeaderAccessor ha = SimpMessageHeaderAccessor
                .create(SimpMessageType.MESSAGE);
        ha.setSessionId(headerAccessor.getSessionId());
        ha.setLeaveMutable(true);
        System.out.println("HOST : " + message.getFrom());
        template.convertAndSendToUser(headerAccessor.getSessionId(), "/sub/message", response, ha.getMessageHeaders());
    }

    @MessageMapping(value = "/msg/{id}")
    @SendTo("/sub/game/{id}")
    public Reply reply1(MyMessage message, @DestinationVariable int id) throws Exception {
        Chess chessGame = chessService.findOne(id);
        if (chessGame == null)
            return new Reply("Error");
        return replyStrategies.getOrDefault(message.getCommand(), new EmptyStrategy()).execute(message, id);
    }
}