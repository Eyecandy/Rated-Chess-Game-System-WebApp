package io.muic.designpattern.model;

/**
 * Created by DevSingh on 11/18/17.
 */
public class Reply {
    private String reply;
    private int turn;
    private String source;
    private String target;
    private String player1;
    private String player2;
    private String fenBoard;

    public Reply(){
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public Reply(String reply) {
        this.reply = reply;
    }

    public Reply(String reply, int turn) {
        this.reply = reply;
        this.turn = turn;
    }

    public Reply(String reply, int turn, String fenBoard) {
        this.reply = reply;
        this.turn = turn;
        this.fenBoard = fenBoard;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public String getFenBoard() {
        return fenBoard;
    }

    public void setFenBoard(String fenBoard) {
        this.fenBoard = fenBoard;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
