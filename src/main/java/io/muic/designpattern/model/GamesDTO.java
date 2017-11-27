package io.muic.designpattern.model;

/**
 * Created by DevSingh on 11/27/17.
 */
public class GamesDTO {
    private int gameID;
    private String hostName;

    public GamesDTO(int gameID, String hostName) {
        this.gameID = gameID;
        this.hostName = hostName;
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
}
