package io.muic.designpattern.model;

import org.springframework.messaging.simp.config.MessageBrokerRegistry;

public class MyMessage {

    private String from;


    public MyMessage(){
    }

    public MyMessage(String from) {
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
