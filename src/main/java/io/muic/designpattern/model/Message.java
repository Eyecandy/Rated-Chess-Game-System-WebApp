package io.muic.designpattern.model;

public class Message {

    private Long from;
    private String message;

    public Message(Long from, String message) {
        this.from = from;
        this.message = message;
    }

    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    //    private Integer startX;
//    private Integer startY;
//
//    private Integer destinationX;
//    private Integer destinationY;
}
