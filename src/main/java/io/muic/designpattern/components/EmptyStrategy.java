package io.muic.designpattern.components;

import io.muic.designpattern.model.MyMessage;
import io.muic.designpattern.model.Reply;

public class EmptyStrategy implements ChessReplyStrategy {
    @Override
    public Reply execute(MyMessage message, int chessId) {
        return new Reply("Error");
    }
}
