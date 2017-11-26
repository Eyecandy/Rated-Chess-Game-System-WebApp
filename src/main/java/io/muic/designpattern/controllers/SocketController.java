package io.muic.designpattern.controllers;

import io.muic.designpattern.model.Chess;
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

    String player1 = "";
    String player2 = "";

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

    @MessageMapping("/create_game")
    @SendTo("/sub/message")
    public Reply create_game(MyMessage message) throws Exception {
        String command = message.getCommand();
        System.out.println(s + " HEY FROM ME");
        if (message.getFrom().equals("get em")) {
            System.out.println(subscriberService.getSubscribers().values());
        }
        Chess chess = new Chess();
        chess.setCurrentPlayer(1);
        chess.setComplete(false);
        System.out.println(chess.getId());
        Reply reply = new Reply("0");
        return reply;

    }

    @MessageMapping(value = "/msg/{id}")
    @SendTo("/sub/game")
    public Reply reply1(SimpMessageHeaderAccessor simpMessageHeaderAccessor, MyMessage message, @DestinationVariable long id) throws Exception {
        Chess chessGame = chessService.findOne(id);
        if (chessGame == null)
            return new Reply("Zis is a Pwobwem");
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
        if (message.getCommand().equals("move")){ // (message.getFrom().equals(player1))
            String player = message.getFrom();
            String reply = "switch";
            String fen = message.getFenBoard();
            String source = message.getSource();
            String target = message.getTarget();
            String player1 = chessGame.getHost().getUsername();
            String player2 = chessGame.getPlayer().getUsername();

            int turn = (userService.findUserByUsername(player).equals(chessGame.getHost())) ? 2 : 1;

            chessGame.setCurrentPlayer(turn);
            chessGame.setFen(fen);
            chessService.saveChess(chessGame);

            Reply move = new Reply(reply, turn, fen);
            move.setSource(source);
            move.setTarget(target);
            move.setPlayer1(player1);
            move.setPlayer2(player2);
            return move;
        }
        return new Reply("Chess");
    }
}