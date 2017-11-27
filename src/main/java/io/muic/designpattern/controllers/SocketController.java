package io.muic.designpattern.controllers;

import io.muic.designpattern.model.Chess;
import io.muic.designpattern.model.GameRequest;
import io.muic.designpattern.model.MyMessage;
import io.muic.designpattern.model.Reply;
import io.muic.designpattern.services.ChessService;
import io.muic.designpattern.services.SubscriberService;
import io.muic.designpattern.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class SocketController {

    @Autowired
    SubscriberService subscriberService;

    @Autowired
    ChessService chessService;

    @Autowired
    UserService userService;

    private SimpMessagingTemplate template;

    @Autowired
    public SocketController(SimpMessagingTemplate template){
        this.template = template;
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
    @SendTo("/sub/game")
    public Reply reply1(SimpMessageHeaderAccessor simpMessageHeaderAccessor, MyMessage message, @DestinationVariable int id) throws Exception {
        Chess chessGame = chessService.findOne(id);
//        Chess chessGame = null;
        if (chessGame == null)
            return new Reply("Zis is a Pwobwem");
        else {
            System.out.println("ok");
            return new Reply("Works");
        }
//        System.out.println("Chess game player : " + message.getFrom());
//        System.out.println(message.getFrom() + " TEST " + id);
//        String s = subscriberService.getSubscribers().get(simpMessageHeaderAccessor.getSessionId());
//        return new Reply("In Game " + s);
//        ChessGame chessGame= ChessGameService.findOne(id);
//        .....
//        if (message.getCommand().equals("reset")) {
//            player1 = "";
//            player2 = "";
//            return new Reply("reset");
//        }
//        if (message.getCommand().equals("start")){
//            if (player1.equals("")){
//                player1 = message.getFrom();
//                System.out.println("player1 set");
//                Reply wait = new Reply("wait");
//                wait.setPlayer1(player1);
//                return wait;
//            }else {
//                player2 = message.getFrom();
//                Reply start = new Reply("start");
//                start.setPlayer1(player1);
//                start.setPlayer2(player2);
//                System.out.println("player2 set");
//                return start;
//            }
//        }
//        if (message.getCommand().equals("move")){ // (message.getFrom().equals(player1))
//            String player = message.getFrom();
//            String reply = "switch";
//            String fen = message.getFenBoard();
//            String source = message.getSource();
//            String target = message.getTarget();
//            String player1 = chessGame.getHost().getUsername();
//            String player2 = chessGame.getPlayer().getUsername();
//
//            int turn = (userService.findUserByUsername(player).equals(chessGame.getHost())) ? 2 : 1;
//
//            chessGame.setCurrentPlayer(turn);
//            chessGame.setFen(fen);
//            chessService.saveChess(chessGame);
//
//            Reply move = new Reply(reply, turn, fen);
//            move.setSource(source);
//            move.setTarget(target);
//            move.setPlayer1(player1);
//            move.setPlayer2(player2);
//            return move;
//        }
//        return new Reply("Chess");
    }
}