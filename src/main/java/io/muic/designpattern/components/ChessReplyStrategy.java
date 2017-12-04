package io.muic.designpattern.components;

import io.muic.designpattern.model.MyMessage;
import io.muic.designpattern.model.Reply;

public interface ChessReplyStrategy {
    Reply execute(MyMessage message, int chessId);
}
