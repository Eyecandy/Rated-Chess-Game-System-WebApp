package io.muic.designpattern.model;

/**
 * Created by DevSingh on 11/29/17.
 */
public class OnlineTuple {
    private String playerOne;
    private String playerTwo;

    public OnlineTuple(){}

    public OnlineTuple(String playerOne, String playerTwo){
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    public String getPlayerTwo() {
        return playerTwo;
    }

    public String getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(String playerOne) {
        this.playerOne = playerOne;
    }
}
