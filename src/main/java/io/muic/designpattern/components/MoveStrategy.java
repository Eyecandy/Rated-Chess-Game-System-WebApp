package io.muic.designpattern.components;

import io.muic.designpattern.model.Chess;
import io.muic.designpattern.model.MyMessage;
import io.muic.designpattern.model.Reply;
import io.muic.designpattern.model.User;
import io.muic.designpattern.services.ChessService;
import io.muic.designpattern.services.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;

public class MoveStrategy implements ChessReplyStrategy {
    private final ChessService chessService;
    private final SubscriberService subscriberService;

    @Autowired
    public MoveStrategy(ChessService chessService, SubscriberService subscriberService) {
        this.chessService = chessService;
        this.subscriberService = subscriberService;
    }
    @Override
    public Reply execute(MyMessage message, int chessId) {
        Chess chessGame = chessService.findOne(chessId);
        String player1 = chessGame.getHost().getUsername();
        String player = message.getFrom();
        String reply = "switch";
        String fen = message.getFenBoard();
        String source = message.getSource();
        String target = message.getTarget();
        String player2 = chessGame.getPlayer().getUsername();

        if (!subscriberService.getUserOnline(player1)) {
            return new Reply("disconnect");
        }
        User p2 = chessGame.getPlayer();
        if (!subscriberService.getUserOnline(p2.getUsername())) {
            System.out.println("P2 offline");
            return new Reply("disconnect");
        }

        int turn = (player.equals(chessGame.getHost().getUsername())) ? 2 : 1;
//        System.out.println(chessGame.getHost().getUsername());
//        System.out.println(chessGame.getPlayer().getUsername());
//        System.out.println(from);
//        System.out.println(turn);

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
