package io.muic.designpattern.checkers;

import javafx.geometry.Pos;

public class Piece {
    private Side side;
    private Position position;



    private PieceType pieceType;



    private Integer directionalSign;


    Piece(Side side,PieceType pieceType,Integer directionalSign) {
        this.side = side;
        this.pieceType = pieceType;
        this.directionalSign = directionalSign;
    }

    public Side getSide() {
        return side;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Integer getDirectionalSign() {
        return directionalSign;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

}
