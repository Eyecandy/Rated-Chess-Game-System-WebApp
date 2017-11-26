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

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public void setHost(User host) {
        this.host = host;
    }

    public void setPlayer(User player) {
        this.player = player;
    }
}
