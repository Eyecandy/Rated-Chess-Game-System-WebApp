package io.muic.designpattern.model;

import org.springframework.messaging.simp.config.MessageBrokerRegistry;

public class MyMessage {

    private String from;
    private String command;
    private String fenBoard;
    private String source, target;

    public MyMessage(){
    }

    public MyMessage(String from) {
        this.from = from;
    }

    public MyMessage(String from,String command) {
        this.from = from;
        this.command = command;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
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

    //    private Integer startX;
//    private Integer startY;
//
//    private Integer destinationX;
//    private Integer destinationY;
}
