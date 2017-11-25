package io.muic.designpattern.controllers;

import io.muic.designpattern.model.Message;
import io.muic.designpattern.model.Reply;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.HashMap;

@Controller
public class SocketController {

    String player1 = "";
    String player2 = "";
    @MessageMapping("/msg") /* /dest/msg */
    @SendTo("/sub/message")
    public Reply reply(Message message) throws Exception {
        if (message.getCommand().equals("reset")) {
            player1 = "";
            player2 = "";
            return new Reply("reset");
        }
        if (message.getCommand().equals("start")){
            if (player1.equals("")){
                player1 = message.getFrom();
                System.out.println("player1 set");
                Reply wait = new Reply("wait");
                wait.setPlayer1(player1);
                return wait;
            }else {
                player2 = message.getFrom();
                Reply start = new Reply("start");
                start.setPlayer1(player1);
                start.setPlayer2(player2);
                System.out.println("player2 set");
                return start;
            }
        }
        if (message.getCommand().equals("move")){
            if (message.getFrom().equals(player1)){
                Reply move = new Reply("switch",2,message.getFenBoard());
                move.setSource(message.getSource());
                move.setTarget(message.getTarget());
                move.setPlayer1(player1);
                move.setPlayer2(player2);
                return move;
            }else {
                Reply move = new Reply("switch",1,message.getFenBoard());
                move.setSource(message.getSource());
                move.setTarget(message.getTarget());
                move.setPlayer1(player1);
                move.setPlayer2(player2);
                return move;
            }

        }
        return new Reply("Chess");
    }
}
