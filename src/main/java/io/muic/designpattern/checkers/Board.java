package io.muic.designpattern.checkers;

public class Board {
    private final Integer SIZE = 8;
    private Integer noBlackPieces = 0;
    private Integer noRedPieces = 0;
    private Side currentSideturn = Side.BLACK;

    private Tile[][] board = new Tile[SIZE][SIZE];

    protected void fillBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int tile = 0;tile < SIZE; tile ++) {
                addPieceToTile(row,tile);
            }
        }
    }

    private void addPieceToTile(int row,int tile) {
        Tile newTile;
        if (row == 3 || row == 4) {
            newTile  = new Tile();
            newTile.setContainsPiece(false);
        }
        else if (row%2 ==0 && tile%2 ==0) {
            newTile = new Tile();
            newTile.setContainsPiece(true);
            newTile.setPiece(createPiece());
        }

        else if (row%2 ==1 && tile%2 ==1) {
            newTile = new Tile();
            newTile.setContainsPiece(true);
            newTile.setPiece(createPiece());
        }
        else {
            newTile  = new Tile();
            newTile.setContainsPiece(false);
        }
        board[row][tile] = newTile;
    }

    private Piece createPiece() {

        if (noBlackPieces == 11) {
            noRedPieces+=1;
            Piece redPiece = new Piece(Side.RED,PieceType.SIMPLE,1);
            return redPiece;
        }
        else {
            noBlackPieces+=1;
            Piece blackPiece = new Piece(Side.BLACK,PieceType.SIMPLE,-1);
            return blackPiece;
        }
    }

    protected Tile[][] getBoard() {
        return board;
    }

    protected void printBoard() {
        for (Tile[] row: board) {
            for (Tile tile:row) {
                Boolean hasPiece = tile.getContainsPiece();
                if (hasPiece) {
                    System.out.print(tile.getPiece().getSide());
                }
                else
                    System.out.print(" X ");
            }
            System.out.println();
        }
    }


    /**
     * @param player the player's Side will be used to check if allowed to move
     * @param piece use to determine pos of piece and
     * @param selectedTile has to be empty
     * @return  move is valid or not  - Boolean
     *
     */
    public Boolean isValidMove(Player player,Piece piece,Tile selectedTile) {

        Position currPosition = piece.getPosition();
        Position selectedPosition = selectedTile.getPosition();
        int x = currPosition.getX();
        int y = currPosition.getY();
        int sx = selectedPosition.getX();
        int sy = selectedPosition.getY();


        if (notPlayersTurn(player)) {
            return false;
        }

        else if (selectedTile.getContainsPiece()) {
            return false;
        }
        return true;

    }

    private boolean notPlayersTurn(Player player) {
        return player.getSide() != currentSideturn;
    }
    //not yet implemented
    private boolean isDiagMove(int x,int y, int sx,int sy) {
        return false;

    }



}
