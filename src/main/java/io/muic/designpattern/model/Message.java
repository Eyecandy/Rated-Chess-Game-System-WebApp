package io.muic.designpattern.model;

public class Message {

    private String from;

    public Message(){
    }

    public Message(String from) {
        this.from = from;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    //    private Integer startX;
//    private Integer startY;
//
//    private Integer destinationX;
//    private Integer destinationY;
}
