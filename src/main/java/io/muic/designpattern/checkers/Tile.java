package io.muic.designpattern.checkers;

public class Tile {

    private Boolean containsPiece;
    private Piece piece = null;



    private Position position;


    public Boolean getContainsPiece() {
        return containsPiece;
    }

    public void setContainsPiece(Boolean containsPiece) {
        this.containsPiece = containsPiece;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Position getPosition() {
        return position;
    }

}
