package io.muic.designpattern.components;

import io.muic.designpattern.model.Chess;
import io.muic.designpattern.model.MyMessage;
import io.muic.designpattern.model.OnlineTuple;
import io.muic.designpattern.model.Reply;
import io.muic.designpattern.services.ChessService;
import io.muic.designpattern.services.OnlineUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResumeStrategy implements ChessReplyStrategy{

    private final OnlineUserService onlineUserService;
    private final ChessService chessService;

    @Autowired
    public ResumeStrategy(OnlineUserService onlineUserService, ChessService chessService) {
        this.onlineUserService = onlineUserService;
        this.chessService = chessService;
    }

    @Override
    public Reply execute(MyMessage message, int chessId) {
        String from = message.getFrom();
        Chess chessGame = chessService.findOne(chessId);
        String player1 = chessGame.getHost().getUsername();
        String player2 = chessGame.getPlayer().getUsername();
//        System.out.println("RESUME");
        OnlineTuple onlinePlayers = onlineUserService.getGame(chessId);
        if (onlinePlayers != null){
            if (from.equals(onlinePlayers.getPlayerOne())){
                return new Reply("wait");
            }else {
                Reply start = new Reply("start");
                start.setPlayer1(player1);
                start.setPlayer2(player2);
                start.setFenBoard(chessGame.getFen());
                start.setTurn(chessGame.getCurrentPlayer());
//                System.out.println("current turn " +chessGame.getCurrentPlayer() );
                onlineUserService.removeGame(chessId);
                return start;
            }
        }else {
            onlineUserService.addGame(chessId, from,"");
            return new Reply("wait");
        }
    }
}
