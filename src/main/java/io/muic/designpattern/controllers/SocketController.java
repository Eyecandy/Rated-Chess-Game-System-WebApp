package io.muic.designpattern.controllers;

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

    @Autowired
    OnlineUserService onlineUserService;

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
    public Reply reply1(SimpMessageHeaderAccessor simpMessageHeaderAccessor, MyMessage message, @DestinationVariable int id) throws Exception {
        Chess chessGame = chessService.findOne(id);
        if (chessGame == null)
            return new Reply("Error");
        String from = message.getFrom();
        String player1 = chessGame.getHost().getUsername();

        if (message.getCommand().equals("resume")){
            System.out.println("RESUME");
            OnlineTuple onlinePlayers = onlineUserService.getGame(id);
            if (onlinePlayers != null){
                if (from.equals(onlinePlayers.getPlayerOne())){
                    return new Reply("wait");
                }else {
                    Reply start = new Reply("start");
                    if (player1.equals(onlinePlayers.getPlayerOne())){
                        start.setPlayer1(player1);
                        start.setPlayer2(chessGame.getPlayer().getUsername());
                    }else {
                        start.setPlayer1(chessGame.getPlayer().getUsername());
                        start.setPlayer2(player1);
                    }
                    start.setFenBoard(chessGame.getFen());
                    start.setTurn(chessGame.getCurrentPlayer());
                    System.out.println("current turn " +chessGame.getCurrentPlayer() );
                    onlineUserService.removeGame(id);
                    return start;
                }
            }else {
                onlineUserService.addGame(id, from,"");
                return new Reply("wait");
            }
        }

        if (!chessGame.isOngoing() && message.getCommand().equals("start")){
            if (chessGame.getHost().getUsername().equals(from)){
                System.out.println("player1 set");
                Reply wait = new Reply("wait");
                wait.setPlayer1(from);
                return wait;
            }else {
                User player2 = userService.findUserByUsername(from);
                if (player2 == null)
                    return new Reply("Error");
                chessGame.setPlayer(player2);
                chessGame.setOngoing(true);
                chessService.saveChess(chessGame);
                Reply start = new Reply("start");
                start.setPlayer1(player1);
                start.setPlayer2(from);
                System.out.println("player2 set");
                return start;
            }
        } else {
            System.out.println("ONGOING");
            System.out.println("P1 online" + subscriberService.getUserOnline(player1));
            if (!subscriberService.getUserOnline(player1)) {
                System.out.println("P1 offline");
                return new Reply("disconnect");
            }
            User p2 = chessGame.getPlayer();
            System.out.println("P2 online" + subscriberService.getUserOnline(p2.getUsername()));
            if (!subscriberService.getUserOnline(p2.getUsername())) {
                System.out.println("P2 offline");
                return new Reply("disconnect");
            }

            if (message.getCommand().equals("move")){
                String player = message.getFrom();
                String reply = "switch";
                String fen = message.getFenBoard();
                String source = message.getSource();
                String target = message.getTarget();
                String player2 = chessGame.getPlayer().getUsername();

                int turn = (player.equals(chessGame.getHost().getUsername())) ? 2 : 1;
                System.out.println(chessGame.getHost().getUsername());
                System.out.println(chessGame.getPlayer().getUsername());
                System.out.println(from);
                System.out.println(turn);

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
        }
        return new Reply("Chess");
    }
}