package io.muic.designpattern.components;

import io.muic.designpattern.model.Chess;
import io.muic.designpattern.model.MyMessage;
import io.muic.designpattern.model.Reply;
import io.muic.designpattern.model.User;
import io.muic.designpattern.services.ChessService;
import io.muic.designpattern.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartStrategy implements ChessReplyStrategy {


    private ChessService chessService;

    private UserService userService;

    @Autowired
    public StartStrategy(ChessService chessService, UserService userService) {
        this.chessService = chessService;
        this.userService = userService;
    }

    @Override
    public Reply execute(MyMessage message, int chessId) {
        String from = message.getFrom();
        Chess chessGame = chessService.findOne(chessId);
        String player1 = chessGame.getHost().getUsername();
        if (chessGame.getHost().getUsername().equals(from)){
//            System.out.println("player1 set");
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
//            System.out.println("player2 set");
            return start;
        }
    }
}
