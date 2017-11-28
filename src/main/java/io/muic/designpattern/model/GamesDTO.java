package io.muic.designpattern.model;

/**
 * Created by DevSingh on 11/27/17.
 */
public class GamesDTO {
    private int gameID;
    private String hostName;
    private String fen;

    public GamesDTO(int gameID, String hostName) {
        this.gameID = gameID;
        this.hostName = hostName;
    }

    public GamesDTO(int gameID, String hostName, String fen) {
        this.gameID = gameID;
        this.hostName = hostName;
        this.fen = fen;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getFen() {
        return fen;
    }

    public void setFen(String fen) {
        this.fen = fen;
    }
}
