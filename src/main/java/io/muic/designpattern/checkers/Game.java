package io.muic.designpattern.checkers;

public class Game {
    Player player1;
    Player player2;
    Board board;

    public Game() {
        player1 = new Player(Side.BLACK);
        player2 = new Player(Side.RED);
        board = new Board();
        board.fillBoard();
    }

    public static void main(String[] args) {
        Board board = new Board();
        board.fillBoard();
        board.printBoard();
    }
}
