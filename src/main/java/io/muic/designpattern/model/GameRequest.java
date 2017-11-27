package io.muic.designpattern.model;

/**
 * Created by DevSingh on 11/27/17.
 */
public class GameRequest {

    private String type = "gameReq";
    private int chessID;

    public GameRequest(){
    }

    public GameRequest(int chessID) {
        this.chessID = chessID;
    }


    public int getChessID() {
        return chessID;
    }

    public void setChessID(int chessID) {
        this.chessID = chessID;
    }

    public String getType() {
        return type;
    }
}
