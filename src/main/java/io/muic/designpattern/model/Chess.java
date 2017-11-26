package io.muic.designpattern.model;

import javax.persistence.*;

@Entity
@Table(name = "chess")
public class Chess {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "chess_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "host")
    private User host;

    @ManyToOne
    @JoinColumn(name = "player")
    private User player;

    @Column(name = "complete")
    private boolean isComplete;

    @Column(name = "currentPlayer")
    private int currentPlayer;

    @Column(name = "fen")
    private String fen;

    public int getId() {
        return id;
    }

    public User getHost() {
        return host;
    }

    public User getPlayer() {
        return player;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public String getFen() {
        return fen;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public void setHost(User host) {
        this.host = host;
    }

    public void setPlayer(User player) {
        this.player = player;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setFen(String fen) {
        this.fen = fen;
    }
}
